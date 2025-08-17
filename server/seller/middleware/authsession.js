import axios from "axios";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import User from "../../buyer/models/User.js"

export const authsession = AsyncHandler(async (req, res, next) => {
    const accessToken = req.headers["x-access-token"];
    if (!accessToken) {
      return res.status(401).json({ message: "Access token missing" });
    }
    const response = await axios.get(
      `https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=${accessToken}`
    );
    const user=await User.findOne({email:response.data.email}).select("_id")
    if (!user)throw new ApiError(401, "Invalid Access Token: User not found");

    req.user = user;
    next();
});
