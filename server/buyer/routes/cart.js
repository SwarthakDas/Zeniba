import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addToCart, clearCart, getCart, removeCartItem, updateCartItem } from "../controllers/cart.js";

export const router=Router()

router.get("/", authsession,getCart);
router.post("/", authsession,addToCart);
router.patch("/",authsession,updateCartItem)
router.delete("/:productId",authsession,removeCartItem)
router.delete("/",authsession,clearCart)