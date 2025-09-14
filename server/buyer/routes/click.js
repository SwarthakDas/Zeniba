import express from "express";
import { authsession } from "../middleware/authsession.js";
import { getMostClicked, trackClick } from "../controllers/click.js";

const router = express.Router();

router.post("/click", authsession, trackClick);
router.get("/most-clicked", authsession, getMostClicked);

export default router;