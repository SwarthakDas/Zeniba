import Product from "../models/Product.js";
import {AsyncHandler} from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import {ApiResponse} from "../utils/ApiResponse.js"
import { getEmbedding, cosineSimilarity } from "../utils/getEmbeddings.js"
import Embedding from "../models/Embedding.js"

export const getProducts = AsyncHandler(async (req, res) => {
  const { category, minPrice, maxPrice, rating, sort } = req.query;

  let query = {};
  if (category) query.category = category;
  if (minPrice) query.price = { ...query.price, $gte: Number(minPrice) };
  if (maxPrice) query.price = { ...query.price, $lte: Number(maxPrice) };
  if (rating) query.rating = { $gte: Number(rating) };

  let productsQuery = Product.find(query).select("-createdAt -updatedAt -__v").populate("seller", "name email -_id");

  if (sort) {
    const sortOptions = sort.split(",").join(" ");
    productsQuery = productsQuery.sort(sortOptions);
  }

  const products = await productsQuery;
  if (!products.length) throw new ApiError(404, "No products found");

  return res
    .status(200)
    .json(new ApiResponse(200, products, "Products fetched successfully"));
});

export const getProductById = AsyncHandler(async (req, res) => {
  const product = await Product.findById(req.params.productid).select("-createdAt -updatedAt -__v").populate("seller", "name email -_id");
  if (!product) throw new ApiError(404, "Product not found");

  return res
    .status(200)
    .json(new ApiResponse(200, product, "Product details fetched successfully"));
});

export const getCategories = AsyncHandler(async (req, res) => {
  const categories = await Product.distinct("category");
  if (!categories.length) throw new ApiError(404, "No categories found");

  return res
    .status(200)
    .json(new ApiResponse(200, categories, "Categories fetched successfully"));
});

export const getBrands = AsyncHandler(async (req, res) => {
  const brands = await Product.distinct("brand");
  if (!brands.length) throw new ApiError(404, "No brands found");

  return res
    .status(200)
    .json(new ApiResponse(200, brands, "Brands fetched successfully"));
});

export const searchProducts = AsyncHandler(async (req, res) => {
  const { q } = req.query;
  if (!q) throw new ApiError(400, "Search query is required");

  let queryEmbedding;
  try {
    const hfEmbedding = await getEmbedding(q);
    queryEmbedding = Array.isArray(hfEmbedding[0]) ? hfEmbedding[0] : hfEmbedding;
  } catch (err) {
    throw new ApiError(500, "Failed to generate query embedding");
  }

  const products = await Product.find({}, "name description embedding seller")
  .populate("seller", "name email -_id")
  .lean();
  if (!products.length) return res.status(200).json(new ApiResponse(200, [], "No products found"));

  const scoredProducts = products.map((p) => ({
    ...p,
    similarity: cosineSimilarity(queryEmbedding, p.embedding || []),
  }));
  scoredProducts.sort((a, b) => b.similarity - a.similarity);

  await Embedding.create({
    user: req.user?._id,
    type: "search",
    query: q,
    embedding: queryEmbedding,
    metadata: { resultsCount: scoredProducts.length },
  });

  return res
    .status(200)
    .json(new ApiResponse(200, scoredProducts, "Search results fetched successfully"));
});

export const searchGuestProducts = AsyncHandler(async (req, res) => {
  const { q } = req.query;
  if (!q) throw new ApiError(400, "Search query is required");

  const products = await Product.find({
    name: { $regex: q, $options: "i" }
  }).select("-createdAt -updatedAt -__v").populate("seller", "name email -_id");

  return res
    .status(200)
    .json(new ApiResponse(200, products, "Search results fetched successfully"));
});

export const getRecommendations = AsyncHandler(async (req, res) => {
  if (!req.user) throw new ApiError(401, "User not authenticated");

  const recommendations = await Product.find().select("-createdAt -updatedAt -__v").populate("seller", "name email -_id").sort({ createdAt: -1 }).limit(5);

  return res
    .status(200)
    .json(new ApiResponse(200, recommendations, "Recommendations fetched successfully"));
});
