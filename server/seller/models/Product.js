import mongoose from "mongoose";

const ProductSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    default: "",
  },
  price: {
    type: Number,
    required: true,
  },
  images: [
    {
      type: String,
      default: "",
    }
  ],
  category: {
    type: String,
  },
  brand: {
    type: String,
    default: "",
  },
  stock: {
    type: Number,
    default: 0,
  },
  seller: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
    required: true,
  }
}, { timestamps: true });

const Product = mongoose.model("Product", ProductSchema);
export default Product;
