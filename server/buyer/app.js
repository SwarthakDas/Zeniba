import express from "express";
import cors from "cors";
import { router as authRoutes } from "./routes/auth.js";
import { router as cartRoutes } from "./routes/cart.js";
import { router as discoverRoutes } from "./routes/discover.js";
import { router as orderhRoutes } from "./routes/order.js";
import { router as reviewRoutes } from "./routes/review.js";
import { router as wishlistRoutes } from "./routes/wishlist.js";
import dotenv from "dotenv"
dotenv.config();

const app = express();

app.use(cors({
  origin: process.env.CLIENT_URL,
  allowedHeaders: ["Authorization", "Content-Type"],
  credentials: true,
}));

app.use(express.json({ limit: "1mb" }));
app.use(express.urlencoded({ extended: true, limit: "1mb" }));

app.use("",authRoutes,cartRoutes,discoverRoutes,orderhRoutes,reviewRoutes,wishlistRoutes);

app.use((err, req, res, next) => {
  const statusCode = err.statusCode || 500;
  res.status(statusCode).json({
    success: false,
    message: err.message || "Internal Server Error",
    errors: err.errors || [],
    stack: process.env.NODE_ENV === "production" ? undefined : err.stack,
  });
});

export default app;