import Wishlist from "../models/Wishlist.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

export const getWishlist = AsyncHandler(async (req, res) => {
  const wishlist = await Wishlist.findOne({ user: req.user._id }).populate("products","-createdAt -updatedAt -seller -__v -stock").select("products");
  if (!wishlist || wishlist.products.length === 0) {
    throw new ApiError(404, "Wishlist is empty");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, wishlist, "Wishlist fetched successfully"));
});

export const addToWishlist = AsyncHandler(async (req, res) => {
  const { productId } = req.body;
  if (!productId) throw new ApiError(400, "Product ID is required");

  const product = await Product.findById(productId);
  if (!product) throw new ApiError(404, "Product not found");

  let wishlist = await Wishlist.findOne({ user: req.user._id });

  if (!wishlist) {
    wishlist = new Wishlist({ user: req.user._id, products: [] });
  }

  if (wishlist.products.includes(productId)) {
    throw new ApiError(400, "Product already in wishlist");
  }

  wishlist.products.push(productId);
  await wishlist.save();

  return res
    .status(200)
    .json(new ApiResponse(200, null, "Product added to wishlist successfully"));
});

export const removeFromWishlist = AsyncHandler(async (req, res) => {
  const { productId } = req.params;

  const wishlist = await Wishlist.findOne({ user: req.user._id });
  if (!wishlist) throw new ApiError(404, "Wishlist not found");

  wishlist.products = wishlist.products.filter(
    (p) => p.toString() !== productId
  );

  await wishlist.save();

  return res
    .status(200)
    .json(new ApiResponse(200, null, "Product removed from wishlist successfully"));
});
