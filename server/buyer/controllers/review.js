import Review from "../models/Review.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

export const addReview = AsyncHandler(async (req, res) => {
  const { productId, rating, comment } = req.body;

  if (!rating || rating < 1 || rating > 5) {
    throw new ApiError(400, "Rating must be between 1 and 5");
  }

  const product = await Product.findById(productId);
  if (!product) throw new ApiError(404, "Product not found");

  const existingReview = await Review.findOne({ product: productId, user: req.user._id });
  if (existingReview) throw new ApiError(400, "You have already reviewed this product");

  let review = await Review.create({
    product: productId,
    user: req.user._id,
    rating,
    comment
  });

   review = await Review.findById(review._id)
    .populate("product", "name description")
    .populate("user", "name")
    .select("-__v -createdAt");

  return res
    .status(201)
    .json(new ApiResponse(201, {product:review.product,user:req.user._id,rating,comment}, "Review added successfully"));
});

export const getReviews = AsyncHandler(async (req, res) => {
  const { productId } = req.params;

  const product = await Product.findById(productId);
  if (!product) throw new ApiError(404, "Product not found");

  const reviews = await Review.find({ product: productId })
    .populate("user", "name")
    .populate("product", "name description")
    .sort({ createdAt: -1 })
    .lean()
    .select("-createdAt -__v");

  return res
    .status(200)
    .json(new ApiResponse(200, reviews, "Reviews fetched successfully"));
});

export const updateReview = AsyncHandler(async (req, res) => {
  const { productId, rating, comment } = req.body;

  let review = await Review.findOne({ product: productId, user: req.user._id });
  if (!review) throw new ApiError(404, "Review not found");

  if (rating) {
    if (rating < 1 || rating > 5) throw new ApiError(400, "Rating must be between 1 and 5");
    review.rating = rating;
  }
  if (comment !== undefined) {
    review.comment = comment;
  }

  await review.save();

  review = await Review.findById(review._id)
    .populate("product", "name description")
    .populate("user", "name")
    .select("-__v -createdAt");

  return res
    .status(200)
    .json(new ApiResponse(200, review, "Review updated successfully"));
});

export const deleteReview = AsyncHandler(async (req, res) => {
  const { productId } = req.params;

  const review = await Review.findOne({ product: productId, user: req.user._id });
  if (!review) throw new ApiError(404, "Review not found");

  await review.deleteOne();

  return res
    .status(200)
    .json(new ApiResponse(200, null, "Review deleted successfully"));
});
