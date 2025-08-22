import { OAuth2Client } from "google-auth-library";
import User from "../models/User.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";
import dotenv from "dotenv"
dotenv.config()

const client = new OAuth2Client(process.env.AUTH_CLIENT_ID);

export const googleAuthMobile = AsyncHandler(async (req, res) => {
  const { idToken } = req.body;
  if (!idToken) throw new ApiError(400, "ID token required");

  const ticket = await client.verifyIdToken({
    idToken,
    audience: process.env.AUTH_CLIENT_ID,
  });
  const payload = ticket.getPayload();

  if (!payload) throw new ApiError(401, "Invalid Google token");

  const { email, name, picture } = payload;

  let user = await User.findOne({ email });
  if (!user) {
    user = await User.create({
      email,
      name,
      pic: picture,
    });
  }

  res.json(new ApiResponse(200, { user }, "Google login successful"));
});
