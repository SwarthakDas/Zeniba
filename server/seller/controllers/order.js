import Order from "../../buyer/models/Order.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

// Seller’s orders
export const getSellerOrders = AsyncHandler(async (req, res) => {
  const sellerId = req.user._id;
  const orders = await Order.find({ "items.seller": sellerId }).populate("items.product");

  return res
    .status(200)
    .json(new ApiResponse(200, orders, "Seller orders fetched"));
});

// Get single order details
export const getOrderDetails = AsyncHandler(async (req, res) => {
  const { id } = req.params;
  const sellerId = req.user._id;

  const order = await Order.findOne({ _id: id, "items.seller": sellerId }).populate("items.product");

  if (!order) {
    throw new ApiError(404, "Order not found or unauthorized");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, order, "Order details fetched"));
});

// Update order status
export const updateOrderStatus = AsyncHandler(async (req, res) => {
  const { id } = req.params;
  const { status } = req.body;
  const sellerId = req.user._id;

  if (!status) {
    throw new ApiError(400, "Status is required");
  }

  const order = await Order.findOneAndUpdate(
    { _id: id, "items.seller": sellerId },
    { $set: { status } },
    { new: true }
  );

  if (!order) {
    throw new ApiError(404, "Order not found or unauthorized");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, order, "Order status updated"));
});
