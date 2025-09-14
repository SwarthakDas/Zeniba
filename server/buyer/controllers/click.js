import ClickedProduct from "../models/ClickedProduct.js";
import Product from "../models/Product.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

export const trackClick = AsyncHandler(async (req, res) => {
  const { productId } = req.body;
  if (!productId) throw new ApiError(400, "Product ID is required");

  const product = await Product.findById(productId);
  if (!product) throw new ApiError(404, "Product not found");

  let clicked = await ClickedProduct.findOne({ user: req.user._id });

  if (!clicked) {
    clicked = new ClickedProduct({ user: req.user._id, clickedProducts: [] });
  }

  const index = clicked.clickedProducts.findIndex(
    (p) => p.product.toString() === productId
  );

  if (index !== -1) {
    clicked.clickedProducts[index].count += 1;
  } else {
    clicked.clickedProducts.push({ product: productId, count: 1 });
  }

  await clicked.save();

  return res
    .status(200)
    .json(new ApiResponse(200, null, "Click tracked successfully"));
});

export const getMostClicked = AsyncHandler(async (req, res) => {
  const clicked = await ClickedProduct.findOne({ user: req.user._id })
    .populate("clickedProducts.product", "-createdAt -updatedAt -__v -stock")
    .select("clickedProducts");

  if (!clicked || clicked.clickedProducts.length === 0) {
    throw new ApiError(404, "No clicked products found");
  }

  const sorted = clicked.clickedProducts.sort((a, b) => b.count - a.count);

  return res
    .status(200)
    .json(new ApiResponse(200, sorted, "Most clicked products fetched successfully"));
});