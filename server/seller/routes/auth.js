import { Router } from "express";
import { login, refreshAccessToken } from "../controllers/auth.js";

export const router = Router();
router.post("/login", login);
router.post("/token-refresh", refreshAccessToken);