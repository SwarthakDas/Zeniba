import mongoose from "mongoose";

const ProductEmbeddingSchema = new mongoose.Schema({
  product: { type: mongoose.Schema.Types.ObjectId, ref: "Product", required: true, unique: true },
  embedding: { type: [Number], required: true },
  metadata: { type: mongoose.Schema.Types.Mixed },
  createdAt: { type: Date, default: Date.now },
});

const ProductEmbedding = mongoose.model("ProductEmbedding", ProductEmbeddingSchema);
export default ProductEmbedding