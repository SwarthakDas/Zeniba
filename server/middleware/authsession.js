import axios from "axios";
import { AsyncHandler } from "../utils/AsyncHandler.js";

export const authsession = AsyncHandler(async (req, res, next) => {
    const accessToken = req.headers["x-access-token"];
    if (!accessToken) {
      return res.status(401).json({ message: "Access token missing" });
    }
    const response = await axios.get(
      `https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=${accessToken}`
    );
    req.user = {email:response.data.email};
    next();
});
