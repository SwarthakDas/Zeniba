import Cart from "../models/Cart.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

// 📌 GET /cart → Get cart items
export const getCart = AsyncHandler(async (req, res) => {
  const cart = await Cart.findOne({ user: req.user._id }).populate("items.product");
  if (!cart) throw new ApiError(404, "Cart is empty");

  return res
    .status(200)
    .json(new ApiResponse(200, cart, "Cart fetched successfully"));
});

// 📌 POST /cart → Add to cart
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
    .json(new ApiResponse(200, cart, "Product added to cart successfully"));
});

// 📌 PATCH /cart/:itemId → Update cart item quantity
export const updateCartItem = AsyncHandler(async (req, res) => {
  const { itemId } = req.params;
  const { quantity } = req.body;
  if (!quantity || quantity < 1) throw new ApiError(400, "Quantity must be at least 1");

  const cart = await Cart.findOne({ user: req.user._id });
  if (!cart) throw new ApiError(404, "Cart not found");

  const item = cart.items.id(itemId);
  if (!item) throw new ApiError(404, "Cart item not found");

  item.quantity = quantity;
  await cart.save();

  return res
    .status(200)
    .json(new ApiResponse(200, cart, "Cart item updated successfully"));
});

// 📌 DELETE /cart/:itemId → Remove from cart
export const removeCartItem = AsyncHandler(async (req, res) => {
  const { itemId } = req.params;

  const cart = await Cart.findOne({ user: req.user._id });
  if (!cart) throw new ApiError(404, "Cart not found");

  const item = cart.items.id(itemId);
  if (!item) throw new ApiError(404, "Cart item not found");

  item.remove();
  await cart.save();

  return res
    .status(200)
    .json(new ApiResponse(200, cart, "Item removed from cart successfully"));
});

// 📌 DELETE /cart → Clear cart
export const clearCart = AsyncHandler(async (req, res) => {
  const cart = await Cart.findOne({ user: req.user._id });
  if (!cart) throw new ApiError(404, "Cart not found");

  cart.items = [];
  await cart.save();

  return res
    .status(200)
    .json(new ApiResponse(200, cart, "Cart cleared successfully"));
});
