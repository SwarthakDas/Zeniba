import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addToWishlist, getWishlist, removeFromWishlist } from "../controllers/wishlist.js";

export const router=Router()

router.get("/", authsession,getWishlist);
router.post("/", authsession,addToWishlist);
router.delete("/:productId", authsession,removeFromWishlist);