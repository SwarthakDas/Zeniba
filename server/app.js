import express from "express";
import cors from "cors";
import session from "express-session";
import dotenv from "dotenv";
import { router as authRoutes } from "./routes/auth.js";

dotenv.config();

const app = express();

app.use(session({
  secret: process.env.SESSION_SECRET,
  resave: false,
  saveUninitialized: true,
}));

app.use(cors({
  origin: process.env.CLIENT_URL,
  methods: ["GET", "POST"],
  allowedHeaders: ["Authorization", "Content-Type"],
  credentials: true,
}));

app.use(express.json({ limit: "1mb" }));
app.use(express.urlencoded({ extended: true, limit: "1mb" }));

app.use("/auth", authRoutes);

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