import Product from "../models/Product.js";
import {AsyncHandler} from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import {ApiResponse} from "../utils/ApiResponse.js"
import { getEmbedding, cosineSimilarity } from "../utils/getEmbeddings.js"
import ProductEmbedding from "../models/ProductEmbedding.js"
import UserEmbedding from "../models/UserEmbedding.js"
import mongoose from "mongoose";
import { Pinecone } from "@pinecone-database/pinecone";

const pc = new Pinecone({ apiKey: process.env.PINECONE_API_KEY });
const index = pc.Index("ecom-embeddings");

const weights = {
  order: 1.0,
  cart: 0.8,
  wishlist: 0.6,
  search: 0.3,
};

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

  const productEmbeddings = await ProductEmbedding.find({})
    .populate("product", "name description seller")
    .lean();

  if (!productEmbeddings.length) {
    return res.status(200).json(new ApiResponse(200, [], "No products found"));
  }

  const scoredProducts = productEmbeddings.map((p) => ({
    ...p,
    similarity: cosineSimilarity(queryEmbedding, p.embedding || []),
  }));
  scoredProducts.sort((a, b) => b.similarity - a.similarity);

  if (req.user?._id) {
    await UserEmbedding.create({
      user: req.user._id,
      item: null,
      type: "search",
      embedding: queryEmbedding,
      metadata: { query: q, resultsCount: scoredProducts.length },
    });
  }

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

  const userEmbeds = await UserEmbedding.find({ user: req.user._id });
  if (!userEmbeds.length) {
    throw new ApiError(404, "No embeddings found for this user");
  }

  console.log("=== USER EMBEDDINGS DEBUG ===");
  console.log("Number of embeddings:", userEmbeds.length);
  userEmbeds.forEach((emb, i) => {
    console.log(`Embedding ${i}: type=${emb.type}, dimension=${emb.embedding?.length}, weight=${weights[emb.type] || 0.5}`);
  });

  let vectors = [];
  for (let emb of userEmbeds) {
    const w = weights[emb.type] || 0.5;
    vectors.push(emb.embedding.map((v) => v * w));
  }

  const dim = vectors[0].length;
  const userVector = Array(dim).fill(0).map((_, i) => vectors.map((vec) => vec[i]).reduce((a, b) => a + b, 0) / vectors.length);

  console.log("=== USER VECTOR DEBUG ===");
  console.log("Final vector dimension:", userVector.length);
  console.log("Vector magnitude:", Math.sqrt(userVector.reduce((sum, v) => sum + v*v, 0)));
  console.log("Sample values:", userVector.slice(0, 10));

  // Check index stats
  const indexStats = await index.describeIndexStats();
  console.log("=== INDEX STATS ===");
  console.log("Total vectors in index:", indexStats.totalVectorCount);
  console.log("Index dimension:", indexStats.dimension);

  const results = await index.query({
    vector: userVector,
    topK: 10,
    includeMetadata: true,
  });

  console.log("=== QUERY RESULTS ===");
  console.log("Matches found:", results.matches?.length || 0);

  console.log("Pinecone results:", results);
  console.log("Number of matches:", results.matches?.length);
  console.log("Product IDs:", results.matches?.map(m => m.metadata?.product));

  const productIds = results.matches.map((m) => m.metadata.product);

  const products = await Product.find({
    _id: { $in: productIds.map((id) => new mongoose.Types.ObjectId(id)) },
  }).select("-__v -updatedAt").populate("seller", "name email -_id");

  console.log("Products found in DB:", products);

  return res.status(200).json(
    new ApiResponse(
      200,
      products,
      "Recommendations fetched successfully"
    )
  );
});