import { Router } from "express";
import {
  googleAuthRedirect,
  googleAuthCallback,
  revokeToken,
  getUserDetails
} from "../controllers/auth.js";
import { authsession } from "../middleware/authsession.js";

export const router = Router();

router.get("/google", googleAuthRedirect);
router.get("/google/callback", googleAuthCallback);
router.get("/revoke",authsession,revokeToken);
router.get("/userdetails",authsession,getUserDetails);