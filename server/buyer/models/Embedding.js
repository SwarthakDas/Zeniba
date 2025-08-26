import mongoose from "mongoose";

const EmbeddingSchema = new mongoose.Schema({
  user: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
  type: {
    type: String,
    enum: ["search", "order", "wishlist", "cart"],
    required: true,
  },
  query: { type: String },
  product: { type: mongoose.Schema.Types.ObjectId, ref: "Product" },
  embedding: { type: [Number], required: true },
  metadata: { type: mongoose.Schema.Types.Mixed },
  createdAt: { type: Date, default: Date.now },
});

const Embedding=mongoose.model("Embedding", EmbeddingSchema);
export default Embedding