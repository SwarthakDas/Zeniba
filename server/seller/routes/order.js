import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { getOrderDetails, getSellerOrders, updateOrderStatus } from "../controllers/order.js";

export const router=Router()

router.get("/orders", authsession,getSellerOrders);
router.post("/orders/:orderid", authsession,getOrderDetails);
router.patch("/orders", authsession,updateOrderStatus);