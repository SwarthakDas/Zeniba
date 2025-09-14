import Order from "../models/Order.js";
import Cart from "../models/Cart.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";
import Razorpay from "razorpay"
import crypto from "crypto"
import { getEmbedding } from "../utils/getEmbeddings.js"
import ProductEmbedding from "../models/ProductEmbedding.js"
import UserEmbedding from "../models/UserEmbedding.js"
import dotenv from "dotenv"
import ClickedProduct from "../models/ClickedProduct.js";
dotenv.config()

const razorpay = new Razorpay({
  key_id: process.env.RAZORPAY_ID_KEY,
  key_secret: process.env.RAZORPAY_SECRET_KEY,
});

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

  try {
    for (let item of populatedOrder.items) {
      let productEmbedding = await ProductEmbedding.findOne({ product: item.product._id });
      if (!productEmbedding) {
        const hfEmbedding = await getEmbedding(item.product.name);
        const embedding = Array.isArray(hfEmbedding[0]) ? hfEmbedding[0] : hfEmbedding;

        productEmbedding = await ProductEmbedding.create({
          product: item.product._id,
          embedding,
          metadata: { name: item.product.name },
        });
      }

      await UserEmbedding.findOneAndUpdate(
        { user: req.user._id, item: item.product._id, type: "order" },
        {
          $set: {
            embedding: productEmbedding.embedding,
            metadata: { quantity: item.quantity },
          },
        },
        { upsert: true, new: true }
      );
    }
  } catch (err) {
    console.error("Order embedding log failed:", err.message);
  }

  try {
    const clicked = await ClickedProduct.findOne({ user: req.user._id });
    if (clicked) {
      const orderedIds = populatedOrder.items.map(i => i.product._id.toString());
      clicked.clickedProducts = clicked.clickedProducts.filter(
        (p) => !orderedIds.includes(p.product.toString())
      );
      await clicked.save();
    }
  } catch (err) {
    console.error("ClickedProduct removal after order failed:", err.message);
  }

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

export const initiatePayment = AsyncHandler(async (req, res) => {
  const { orderid } = req.body;
  if (!orderid) throw new ApiError(400, "Order ID required");

  const order = await Order.findById(orderid);
  if (!order) throw new ApiError(404, "Order not found");

  if (order.paymentStatus !== "pending") {
    throw new ApiError(400, "Payment already processed");
  }

  const options = {
    amount: order.totalPrice * 100, // in paise
    currency: "INR",
    receipt: `order_rcpt_${order._id}`,
  };

  const razorpayOrder = await razorpay.orders.create(options);

  return res.status(200).json(
    new ApiResponse(200, {
      orderId: order._id,
      razorpayOrderId: razorpayOrder.id,
      amount: options.amount,
      currency: options.currency,
      key: process.env.RAZORPAY_ID_KEY,
    }, "Payment initiated")
  );
});

export const verifyPayment = AsyncHandler(async (req, res) => {
  const { orderid, razorpay_order_id, razorpay_payment_id, razorpay_signature } = req.body;

  if (!orderid || !razorpay_order_id || !razorpay_payment_id || !razorpay_signature) {
    throw new ApiError(400, "Missing payment details");
  }

  const order = await Order.findById(orderid);
  if (!order) throw new ApiError(404, "Order not found");

  const body = razorpay_order_id + "|" + razorpay_payment_id;
  const expectedSignature = crypto
    .createHmac("sha256", process.env.RAZORPAY_SECRET_KEY)
    .update(body.toString())
    .digest("hex");

  if (expectedSignature === razorpay_signature) {
    order.paymentStatus = "paid";
    order.status = "processing";
    await order.save();

    return res
      .status(200)
      .json(new ApiResponse(200, order, "Payment verified successfully"));
  } else {
    order.paymentStatus = "failed";
    await order.save();

    throw new ApiError(400, "Invalid payment signature");
  }
});
