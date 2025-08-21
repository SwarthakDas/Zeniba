import Review from "../models/Review.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

/**
 * 📌 POST /products/:id/reviews → Add review
 */
export const addReview = AsyncHandler(async (req, res) => {
  const { id: productId } = req.params;
  const { rating, comment } = req.body;

  if (!rating || rating < 1 || rating > 5) {
    throw new ApiError(400, "Rating must be between 1 and 5");
  }

  const product = await Product.findById(productId);
  if (!product) throw new ApiError(404, "Product not found");

  const existingReview = await Review.findOne({ product: productId, user: req.user._id });
  if (existingReview) throw new ApiError(400, "You have already reviewed this product");

  const review = await Review.create({
    product: productId,
    user: req.user._id,
    rating,
    comment
  });

  return res
    .status(201)
    .json(new ApiResponse(201, review, "Review added successfully"));
});

/**
 * 📌 GET /products/:id/reviews → Get reviews
 */
export const getReviews = AsyncHandler(async (req, res) => {
  const { id: productId } = req.params;

  const product = await Product.findById(productId);
  if (!product) throw new ApiError(404, "Product not found");

  const reviews = await Review.find({ product: productId })
    .populate("user", "name pic")
    .sort({ createdAt: -1 });

  return res
    .status(200)
    .json(new ApiResponse(200, reviews, "Reviews fetched successfully"));
});

/**
 * 📌 PATCH /reviews/:id → Edit review
 */
export const updateReview = AsyncHandler(async (req, res) => {
  const { id } = req.params;
  const { rating, comment } = req.body;

  const review = await Review.findOne({ _id: id, user: req.user._id });
  if (!review) throw new ApiError(404, "Review not found");

  if (rating) {
    if (rating < 1 || rating > 5) throw new ApiError(400, "Rating must be between 1 and 5");
    review.rating = rating;
  }
  if (comment !== undefined) {
    review.comment = comment;
  }

  await review.save();

  return res
    .status(200)
    .json(new ApiResponse(200, review, "Review updated successfully"));
});

/**
 * 📌 DELETE /reviews/:id → Delete review
 */
export const deleteReview = AsyncHandler(async (req, res) => {
  const { id } = req.params;

  const review = await Review.findOne({ _id: id, user: req.user._id });
  if (!review) throw new ApiError(404, "Review not found");

  await review.deleteOne();

  return res
    .status(200)
    .json(new ApiResponse(200, null, "Review deleted successfully"));
});
