import { Router } from "express";
import { login } from "../controllers/auth.js";

export const router = Router();
router.post("/login", login);