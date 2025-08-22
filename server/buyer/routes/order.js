import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { cancelOrder, checkout, getOrderById, getOrders, placeOrder } from "../controllers/order.js";

export const router=Router()

router.get("/checkout", authsession,checkout);
router.post("/orders", authsession,placeOrder);
router.get("/orders", authsession,getOrders);
router.get("/orders/:orderid", authsession,getOrderById);
router.patch("/orders/:orderid/cancel", authsession,cancelOrder);