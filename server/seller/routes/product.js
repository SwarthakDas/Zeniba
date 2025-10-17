import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addImages, addProduct, deleteProduct, getMyProducts, updateProduct } from "../controllers/product.js";
import upload from "../middleware/multer.js";

export const router=Router()

router.post("/product", authsession,addProduct);
router.patch("/product/:productid", authsession,updateProduct);
router.delete("/product/:productid", authsession,deleteProduct);
router.get("/product",authsession,getMyProducts)
router.post('/upload-image/:productid',authsession, upload.array('image',5), addImages);