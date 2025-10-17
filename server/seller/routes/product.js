import { Router } from "express";
import { authsession } from "../middleware/authsession.js";
import { addImages, addProduct, deleteProduct, getMyProducts, updateProduct } from "../controllers/product.js";
import upload from "../middleware/multer.js";

export const router=Router()

router.post("/", authsession,addProduct);
router.patch("/:productid", authsession,updateProduct);
router.delete("/:productid", authsession,deleteProduct);
router.get("/",authsession,getMyProducts)
router.post('/upload-image/:productid',authsession, upload.array('image',5), addImages);