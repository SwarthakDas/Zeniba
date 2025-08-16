import { Router } from "express";
import {
  googleAuthRedirect,
  googleAuthCallback,
  getUserDetails,
  logout
} from "../controllers/auth.js";
import { authsession } from "../middleware/authsession.js";

export const router = Router();

router.get("/google", googleAuthRedirect);
router.get("/google/callback", googleAuthCallback);
router.get("/logout",authsession,logout);
router.get("/userdetails",authsession,getUserDetails);