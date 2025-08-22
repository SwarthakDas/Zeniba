import Cart from "../models/Cart.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

export const getCart = AsyncHandler(async (req, res) => {
  const cart = await Cart.findOne({ user: req.user._id }).populate("items.product","name description price");
  if (!cart||cart.items.length===0) throw new ApiError(200, "Cart is empty");

  return res
    .status(200)
    .json(new ApiResponse(200, cart.items, "Cart fetched successfully"));
});

export const addToCart = AsyncHandler(async (req, res) => {
  const { productId, quantity = 1 } = req.body;
  if (!productId) throw new ApiError(400, "Product ID is required");

  const product = await Product.findById(productId);
  if (!product) throw new ApiError(404, "Product not found");

  let cart = await Cart.findOne({ user: req.user._id });

  if (!cart) {
    cart = new Cart({ user: req.user._id, items: [] });
  }

  const existingItem = cart.items.find(item => item.product.toString() === productId);

  if (existingItem) {
    existingItem.quantity += quantity;
  } else {
    cart.items.push({ product: productId, quantity });
  }

  await cart.save();

  return res
    .status(200)
    .json(new ApiResponse(200, cart.items, "Product added to cart successfully"));
});

export const updateCartItem = AsyncHandler(async (req, res) => {
  const { productId,quantity } = req.body;
  if (!quantity || quantity < 1) throw new ApiError(400, "Quantity must be at least 1");

  const cart = await Cart.findOne({ user: req.user._id });
  if (!cart) throw new ApiError(404, "Cart not found");

  const item = cart.items.find(i => i.product.toString() === productId);
  if (!item) throw new ApiError(404, "Cart item not found");

  item.quantity = quantity;
  await cart.save();

  return res
    .status(200)
    .json(new ApiResponse(200, cart.items, "Cart item updated successfully"));
});

export const removeCartItem = AsyncHandler(async (req, res) => {
  const { productId } = req.params;

  const cart = await Cart.findOne({ user: req.user._id });
  if (!cart) throw new ApiError(404, "Cart not found");

  const item = cart.items.find(i => i.product.toString() === productId);
  if (!item) throw new ApiError(404, "Cart item not found");

  cart.items = cart.items.filter(i => i.product.toString() !== productId);
  await cart.save();

  return res
    .status(200)
    .json(new ApiResponse(200, cart.items, "Item removed from cart successfully"));
});

export const clearCart = AsyncHandler(async (req, res) => {
  const cart = await Cart.findOne({ user: req.user._id });
  if (!cart) throw new ApiError(404, "Cart not found");

  cart.items = [];
  await cart.save();

  return res
    .status(200)
    .json(new ApiResponse(200, cart, "Cart cleared successfully"));
});
