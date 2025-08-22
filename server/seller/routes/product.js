import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addProduct, deleteProduct, getMyProducts, updateProduct } from "../controllers/product.js";

export const router=Router()

router.post("/product", authsession,addProduct);
router.patch("/product/:productid", authsession,updateProduct);
router.delete("/product/:productid", authsession,deleteProduct);
router.get("/product",authsession,getMyProducts)