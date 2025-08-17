import Product from "../models/Product.js";
import {AsyncHandler} from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import {ApiResponse} from "../utils/ApiResponse.js"

// 📌 GET /products → List products with filters
export const getProducts = AsyncHandler(async (req, res) => {
  const { category, minPrice, maxPrice, rating, sort } = req.query;

  let query = {};
  if (category) query.category = category;
  if (minPrice) query.price = { ...query.price, $gte: Number(minPrice) };
  if (maxPrice) query.price = { ...query.price, $lte: Number(maxPrice) };
  if (rating) query.rating = { $gte: Number(rating) };

  let productsQuery = Product.find(query);

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

// 📌 GET /products/:id → Get single product details
export const getProductById = AsyncHandler(async (req, res) => {
  const product = await Product.findById(req.params.id);
  if (!product) throw new ApiError(404, "Product not found");

  return res
    .status(200)
    .json(new ApiResponse(200, product, "Product details fetched successfully"));
});

// 📌 GET /categories → List all categories
export const getCategories = AsyncHandler(async (req, res) => {
  const categories = await Product.distinct("category");
  if (!categories.length) throw new ApiError(404, "No categories found");

  return res
    .status(200)
    .json(new ApiResponse(200, categories, "Categories fetched successfully"));
});

// 📌 GET /brands → List all brands
export const getBrands = AsyncHandler(async (req, res) => {
  const brands = await Product.distinct("brand");
  if (!brands.length) throw new ApiError(404, "No brands found");

  return res
    .status(200)
    .json(new ApiResponse(200, brands, "Brands fetched successfully"));
});

// 📌 GET /search?q=... → Search products by keyword
export const searchProducts = AsyncHandler(async (req, res) => {
  const { q } = req.query;
  if (!q) throw new ApiError(400, "Search query is required");

  const products = await Product.find({
    name: { $regex: q, $options: "i" }
  });

  return res
    .status(200)
    .json(new ApiResponse(200, products, "Search results fetched successfully"));
});

// 📌 GET /recommendations → Personalized recommendations
export const getRecommendations = AsyncHandler(async (req, res) => {
  if (!req.user) throw new ApiError(401, "User not authenticated");

  // Simple placeholder: recommend top 5 latest products
  const recommendations = await Product.find().sort({ createdAt: -1 }).limit(5);

  return res
    .status(200)
    .json(new ApiResponse(200, recommendations, "Recommendations fetched successfully"));
});
