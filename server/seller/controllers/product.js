import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";
import ProductEmbedding from "../models/ProductEmbedding.js"
import { getEmbedding } from "../utils/getEmbeddings.js"

export const addProduct = AsyncHandler(async (req, res) => {
  const { name, description, price, stock,category,brand } = req.body;
  const sellerId = req.user._id;

  if (!name || !price || !stock) {
    throw new ApiError(400, "Name, price and stock are required");
  }

  const product = await Product.create({
    name,
    description,
    price,
    category,
    brand,
    stock,
    seller: sellerId,
  });

  try {
    const textForEmbedding = `${name} ${description || ""} ${brand || ""} ${category || ""}`;
    const vector = await getEmbedding(textForEmbedding);
    const embedding = Array.isArray(vector[0]) ? vector[0] : vector;

    await ProductEmbedding.create({
      product: product._id,
      embedding,
      metadata: {
        seller: sellerId,
        price,
        stock,
        brand,
        category,
      },
    });
  } catch (err) {
    console.error("Product embedding failed:", err.message);
  }

  return res
    .status(201)
    .json(new ApiResponse(201, product, "Product added successfully"));
});

export const updateProduct = AsyncHandler(async (req, res) => {
  const { productid } = req.params;
  const sellerId = req.user._id;

  const product = await Product.findOneAndUpdate(
    { _id: productid, seller: sellerId },
    { $set: req.body }, //{ name, description, price, stock,category,brand }
    { new: true }
  );

  if (!product) {
    throw new ApiError(404, "Product not found or unauthorized");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, product, "Product updated successfully"));
});

export const deleteProduct = AsyncHandler(async (req, res) => {
  const { productid } = req.params;
  const sellerId = req.user._id;

  const product = await Product.findOneAndDelete({ _id: productid, seller: sellerId });

  if (!product) {
    throw new ApiError(404, "Product not found or unauthorized");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, null, "Product deleted successfully"));
});

export const getMyProducts = AsyncHandler(async (req, res) => {
  const sellerId = req.user._id;
  const products = await Product.find({ seller: sellerId });

  return res
    .status(200)
    .json(new ApiResponse(200, products, "Seller products fetched"));
});
