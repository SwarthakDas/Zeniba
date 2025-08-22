import Order from "../models/Order.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

export const getSellerOrders = AsyncHandler(async (req, res) => {
  const sellerId = req.user._id;
  const orders = await Order.find({ "seller": sellerId }).populate("items.product","-seller -createdAt -updatedAt -__v").populate("user","name email");

  return res
    .status(200)
    .json(new ApiResponse(200, orders, "Seller orders fetched"));
});

export const getOrderDetails = AsyncHandler(async (req, res) => {
  const { orderid } = req.params;
  const sellerId = req.user._id;

  const order = await Order.findOne({ _id: orderid, "items.seller": sellerId }).populate("items.product");

  if (!order) {
    throw new ApiError(404, "Order not found or unauthorized");
  }

  return res
    .status(200)
    .json(new ApiResponse(200, order, "Order details fetched"));
});

export const updateOrderStatus = AsyncHandler(async (req, res) => {
  const { orderid,status } = req.body;
  const sellerId = req.user._id;

  if (!status) {
    throw new ApiError(400, "Status is required");
  }

  const order = await Order.findOneAndUpdate(
    { _id: orderid, "items.seller": sellerId },
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
