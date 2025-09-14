import mongoose from "mongoose";

const ClickedProductSchema = new mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
    required: true,
    unique: true,
  },
  clickedProducts: [
    {
      product: { type: mongoose.Schema.Types.ObjectId, ref: "Product", required: true },
      count: { type: Number, default: 0 },
    },
  ],
}, { timestamps: true });

const ClickedProduct = mongoose.model("ClickedProduct", ClickedProductSchema);
export default ClickedProduct;
