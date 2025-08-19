import Product from "../../buyer/models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

// Add new product
export const addProduct = AsyncHandler(async (req, res) => {
  const { name, description, price, stock } = req.body;
  const sellerId = req.user._id;

  if (!name || !price || !stock) {
    throw new ApiError(400, "Name, price and stock are required");
  }

  const product = await Product.create({
    name,
    description,
    price,
    stock,
    seller: sellerId,
  });

  return res
    .status(201)
    .json(new ApiResponse(201, product, "Product added successfully"));
});

// Update product
export const updateProduct = AsyncHandler(async (req, res) => {
  const { id } = req.params;
  const sellerId = req.user._id;

  const product = await Product.findOneAndUpdate(
    { _id: id, seller: sellerId },
    { $set: req.body },
    { new: true }
  );

  if (!product) {
    throw new ApiError(404, "Product not found or unauthorized");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, product, "Product updated successfully"));
});

// Delete product
export const deleteProduct = AsyncHandler(async (req, res) => {
  const { id } = req.params;
  const sellerId = req.user._id;

  const product = await Product.findOneAndDelete({ _id: id, seller: sellerId });

  if (!product) {
    throw new ApiError(404, "Product not found or unauthorized");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, null, "Product deleted successfully"));
});

// Get seller’s own products
export const getMyProducts = AsyncHandler(async (req, res) => {
  const sellerId = req.user._id;
  const products = await Product.find({ seller: sellerId });

  return res
    .status(200)
    .json(new ApiResponse(200, products, "Seller products fetched"));
});
