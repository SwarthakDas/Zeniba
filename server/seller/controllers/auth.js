import { OAuth2Client } from "google-auth-library";
import User from "../models/User.js";
import { AsyncHandler } from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import { ApiResponse } from "../utils/ApiResponse.js";

const client = new OAuth2Client(process.env.WEB_CLIENT_ID);

const generateAccessAndRefreshToken=async(id)=>{
  try {
    const user=await User.findById(id)
    if(!user)throw new ApiError(404, "User not found while generating tokens");
    const accessToken=user.generateAccessToken()
    const refreshToken=user.generateRefreshToken()

    return {accessToken, refreshToken}
  } catch (error) {
    throw new ApiError(500, "Something went wrong while generating refresh and access token")
  }
}

export const login = AsyncHandler(async (req, res) => {
  const { idToken } = req.body;
  if (!idToken) throw new ApiError(400, "ID token required");

  const ticket = await client.verifyIdToken({
    idToken,
    audience: [
      process.env.WEB_CLIENT_ID,
      process.env.ANDROID_CLIENT_ID
    ],
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

  const {accessToken, refreshToken}=await generateAccessAndRefreshToken(user._id)

  res.json(new ApiResponse(200, {accessToken, refreshToken}, "Google login successful"));
});

export const refreshAccessToken = AsyncHandler(async (req, res) => {
  const { refreshToken } = req.body;
  if (!refreshToken) throw new ApiError(400, "Refresh token required");

  const user = await User.findOne({ refreshToken });
  if (!user) throw new ApiError(401, "Invalid refresh token");

  try {
    const newAccessToken = user.generateAccessToken();
    res.json(new ApiResponse(200, { accessToken: newAccessToken }, "Access token refreshed"));
  } catch (error) {
    throw new ApiError(500, "Something went wrong while refreshing access token");
  }
});