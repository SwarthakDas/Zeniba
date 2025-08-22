import Order from "../models/Order.js";
import Cart from "../models/Cart.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

export const checkout = AsyncHandler(async (req, res) => {
  const cart = await Cart.findOne({ user: req.user._id }).populate("items.product","-createdAt -updatedAt -__v -stock")
  if (!cart || cart.items.length === 0) throw new ApiError(404, "Cart is empty");

  let totalPrice = 0;
  cart.items.forEach(item => {
    if (item.product.stock < item.quantity) {
      throw new ApiError(400, `Not enough stock for ${item.product.name}`);
    }
    totalPrice += item.product.price * item.quantity;
  });

  return res
    .status(200)
    .json(new ApiResponse(200, { items: cart.items, totalPrice }, "Checkout summary fetched"));
});

export const placeOrder = AsyncHandler(async (req, res) => {
  const { shippingAddress } = req.body;
  if (!shippingAddress) throw new ApiError(400, "Shipping address required");

  const cart = await Cart.findOne({ user: req.user._id }).populate("items.product","-createdAt -updatedAt -__v -stock");
  if (!cart || cart.items.length === 0) throw new ApiError(404, "Cart is empty");

  let totalPrice = 0;
  cart.items.forEach(item => {
    if (item.product.stock < item.quantity) {
      throw new ApiError(400, `Not enough stock for ${item.product.name}`);
    }
    totalPrice += item.product.price * item.quantity;
  });

  const order = await Order.create({
    user: req.user._id,
    items: cart.items.map(i => ({ product: i.product._id, quantity: i.quantity })),
    totalPrice,
    shippingAddress,
    paymentStatus: "pending",
    status: "pending",
    seller: cart.items[0].product.seller
  });

  for (let item of cart.items) {
    await Product.findByIdAndUpdate(item.product._id, { $inc: { stock: -item.quantity } });
  }
  cart.items = [];
  await cart.save();

  const populatedOrder = await Order.findById(order._id)
    .select("-createdAt -updatedAt -__v")
    .populate({
      path: "items.product",
      select: "-createdAt -updatedAt -__v -stock"
    });

  return res
    .status(201)
    .json(new ApiResponse(201, populatedOrder, "Order placed successfully"));
});

export const getOrders = AsyncHandler(async (req, res) => {
  const orders = await Order.find({ user: req.user._id }).select("-createdAt -updatedAt -__v").sort({ createdAt: -1 }).populate("items.product", "name price").populate("user", "name email");
  if (!orders.length) throw new ApiError(404, "No orders found");

  return res
    .status(200)
    .json(new ApiResponse(200, orders, "Orders fetched successfully"));
});

export const getOrderById = AsyncHandler(async (req, res) => {
  const order = await Order.findOne({ _id: req.params.orderid, user: req.user._id }).select("-createdAt -updatedAt -__v").populate("items.product", "name price").populate("user", "name email")
  if (!order) throw new ApiError(404, "Order not found");

  return res
    .status(200)
    .json(new ApiResponse(200, order, "Order details fetched successfully"));
});

export const cancelOrder = AsyncHandler(async (req, res) => {
  const order = await Order.findOne({ _id: req.params.orderid, user: req.user._id });
  if (!order) throw new ApiError(404, "Order not found");

  if (order.status !== "pending") {
    throw new ApiError(400, "Only pending orders can be cancelled");
  }

  order.status = "cancelled";
  await order.save();

  // restore stock
  for (let item of order.items) {
    await Product.findByIdAndUpdate(item.product, { $inc: { stock: item.quantity } });
  }

  return res
    .status(200)
    .json(new ApiResponse(200, order, "Order cancelled successfully"));
});

/**
 * 📌 POST /payment/initiate → Initiate payment
 * (Mock integration for now)
 */
export const initiatePayment = AsyncHandler(async (req, res) => {
  const { orderId } = req.body;
  if (!orderId) throw new ApiError(400, "Order ID required");

  const order = await Order.findById(orderId);
  if (!order) throw new ApiError(404, "Order not found");

  if (order.paymentStatus !== "pending") {
    throw new ApiError(400, "Payment already processed");
  }

  // Mock payment gateway response
  const paymentSession = {
    orderId: order._id,
    amount: order.totalPrice,
    paymentUrl: `https://mockpay.com/session/${order._id}`
  };

  return res
    .status(200)
    .json(new ApiResponse(200, paymentSession, "Payment initiated"));
});

/**
 * 📌 POST /payment/verify → Verify payment
 * (Mock verification)
 */
export const verifyPayment = AsyncHandler(async (req, res) => {
  const { orderId, success } = req.body;
  if (!orderId) throw new ApiError(400, "Order ID required");

  const order = await Order.findById(orderId);
  if (!order) throw new ApiError(404, "Order not found");

  if (success) {
    order.paymentStatus = "paid";
    order.status = "processing";
  } else {
    order.paymentStatus = "failed";
  }

  await order.save();

  return res
    .status(200)
    .json(new ApiResponse(200, order, "Payment status updated"));
});
