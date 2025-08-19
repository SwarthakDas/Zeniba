import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addToWishlist, getWishlist, removeFromWishlist } from "../controllers/wishlist.js";

export const router=Router()

router.get("wishlist", authsession,getWishlist);
router.post("wishlist", authsession,addToWishlist);
router.delete("/wishlist/:productId", authsession,removeFromWishlist);