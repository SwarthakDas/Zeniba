import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addReview, deleteReview, getReviews, updateReview } from "../controllers/review.js";

export const router=Router()

router.post("/reviews", authsession,addReview);
router.get("/reviews/:productId", authsession,getReviews);
router.patch("/reviews", authsession,updateReview);
router.delete("/reviews/:productId", authsession,deleteReview);