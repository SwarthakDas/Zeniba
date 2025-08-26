import { AsyncHandler } from "../utils/AsyncHandler.js";
import User from "../models/User.js";
import { ApiError } from "../utils/ApiError.js";
import jwt from "jsonwebtoken"

export const authsession = AsyncHandler(async (req, res, next) => {
    const token = req.headers.authorization;
    if (!token)throw new ApiError(401, "Unauthorized request: No token provided");

    const decodedToken= jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);
    if(!decodedToken)throw new ApiError(401, "Invalid or Expired Access Token");

    const user=await User.findById(decodedToken?._id).select("_id")
    if (!user)throw new ApiError(401, "Invalid Access Token: User not found");

    req.user = user;
    next();
});
