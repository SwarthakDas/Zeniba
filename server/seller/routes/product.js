import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addProduct, deleteProduct, getMyProducts, updateProduct } from "../controllers/product.js";

export const router=Router()

router.post("/product", authsession,addProduct);
router.patch("/product/:id", authsession,updateProduct);
router.delete("/product/:id", authsession,deleteProduct);
router.get("/product",authsession,getMyProducts)