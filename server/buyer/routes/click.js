import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { getMostClicked, trackClick } from "../controllers/click.js";

export const router = Router()

router.post("/", authsession, trackClick);
router.get("/most-clicked", authsession, getMostClicked);