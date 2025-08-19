import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { checkout, getOrderById, getOrders, placeOrder } from "../controllers/order.js";

export const router=Router()

router.post("/checkout", authsession,checkout);
router.post("/orders", authsession,placeOrder);
router.get("/orders", authsession,getOrders);
router.get("/orders/:id", authsession,getOrderById);