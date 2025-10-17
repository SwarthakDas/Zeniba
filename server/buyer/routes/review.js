import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addReview, deleteReview, getReviews, updateReview } from "../controllers/review.js";

export const router=Router()

router.post("/", authsession,addReview);
router.get("/:productId", authsession,getReviews);
router.patch("/", authsession,updateReview);
router.delete("/:productId", authsession,deleteReview);