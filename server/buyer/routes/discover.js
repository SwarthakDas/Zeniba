import { Router } from "express";
import {getProductById, getProducts,getCategories,getBrands,searchProducts,getRecommendations, searchGuestProducts} from "../controllers/discover.js"
import { authsession } from "../middleware/authsession.js";

export const router=Router()

router.get("/products", getProducts);
router.get("/products/:productid", getProductById);
router.get("/categories", getCategories);
router.get("/brands", getBrands);
router.get("/search",authsession, searchProducts);
router.get("/guest-search", searchGuestProducts);
router.get("/recommendations", authsession, getRecommendations);