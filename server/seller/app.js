import express from "express";
import cors from "cors";
import dotenv from "dotenv";
import { router as authRoutes } from "./routes/auth.js";
import { router as orderRoutes } from "./routes/order.js";
import {router as productRoutes} from "./routes/product.js"

dotenv.config();

const app = express();

app.use(cors({
  origin: process.env.CLIENT_URL,
  methods: ["GET", "POST"],
  allowedHeaders: ["Authorization", "Content-Type"],
  credentials: true,
}));

app.use(express.json({ limit: "1mb" }));
app.use(express.urlencoded({ extended: true, limit: "1mb" }));

app.use("/auth", authRoutes);
app.use("", orderRoutes,productRoutes);

app.use((err, req, res, next) => {
  const statusCode = err.statusCode || 500;
  res.status(statusCode).json({
    success: false,
    message: err.message || "Internal Server Error",
    errors: err.errors || [],
    stack: process.env.NODE_ENV === "production" ? undefined : err.stack,
  });
});

export {app};