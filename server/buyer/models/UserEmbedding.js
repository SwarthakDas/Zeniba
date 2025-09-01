import mongoose from "mongoose";

const UserEmbeddingSchema = new mongoose.Schema({
  user: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
  item: { type: mongoose.Schema.Types.ObjectId, ref: "Product", required: true }, // unique item per user
  type: {
    type: String,
    enum: ["search", "order", "wishlist", "cart"],
    required: true,
  },
  embedding: { type: [Number], required: true },
  metadata: { type: mongoose.Schema.Types.Mixed },
  createdAt: { type: Date, default: Date.now },
});

UserEmbeddingSchema.index({ user: 1, item: 1, type: 1 }, { unique: true });

const UserEmbedding = mongoose.model("UserEmbedding", UserEmbeddingSchema);
export default UserEmbedding