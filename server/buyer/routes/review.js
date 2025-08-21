import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addReview, deleteReview, getReviews, updateReview } from "../controllers/review.js";

export const router=Router()

router.post("/products/:id/reviews", authsession,addReview);
router.get("/products/:id/reviews", authsession,getReviews);
router.patch("/reviews/:id", authsession,updateReview);
router.delete("/reviews/:id", authsession,deleteReview);