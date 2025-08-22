import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addToCart, clearCart, getCart, removeCartItem, updateCartItem } from "../controllers/cart.js";

export const router=Router()

router.get("/cart", authsession,getCart);
router.post("/cart", authsession,addToCart);
router.patch("/cart",authsession,updateCartItem)
router.delete("/cart/:productId",authsession,removeCartItem)
router.delete("/cart",authsession,clearCart)