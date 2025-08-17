import { google } from "googleapis";
import crypto from "crypto";
import https from "https";
import {AsyncHandler} from "../utils/AsyncHandler.js";
import { ApiError } from "../utils/ApiError.js";
import {ApiResponse} from "../utils/ApiResponse.js"
import User from "../models/User.js";

const oauth2Client = new google.auth.OAuth2(
  process.env.AUTH_CLIENT_ID,
  process.env.AUTH_CLIENT_SECRET,
  process.env.AUTH_REDIRECT_URL
);

const SCOPES = [
  'https://www.googleapis.com/auth/userinfo.email',
  'https://www.googleapis.com/auth/userinfo.profile'
];

export const googleAuthRedirect = AsyncHandler(async (req, res) => {
  const state = crypto.randomBytes(32).toString("hex");

  const authUrl = oauth2Client.generateAuthUrl({
    access_type: "offline",
    scope: SCOPES,
    include_granted_scopes: true,
    state
  });

  res.redirect(authUrl);
});

export const googleAuthCallback = AsyncHandler(async (req, res) => {
  const { code } = req.query;

  const { tokens } = await oauth2Client.getToken(code);
  oauth2Client.setCredentials(tokens);

  const oauth2 = google.oauth2({ version: 'v2', auth: oauth2Client });
  const { data } = await oauth2.userinfo.get();

  let user = await User.findOne({ email: data.email });

  if (!user) {
    user = await User.create({
      name: data.name,
      email: data.email,
    });
  }
  return res.status(200).json(
    new ApiResponse(200,{access_token:tokens.access_token},"Authentication successful")
  )
});

export const logout = AsyncHandler((req, res) => {
  const access_token = req.headers["x-access-token"];
  const refresh_token = req.headers["x-refresh-token"];

  if (!access_token && !refresh_token) {
    return res.status(400).send("No tokens found to revoke");
  }

  const revokeToken = (token) => {
    return new Promise((resolve, reject) => {
      const postData = `token=${token}`;
      const options = {
        host: "oauth2.googleapis.com",
        port: 443,
        path: "/revoke",
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "Content-Length": Buffer.byteLength(postData),
        },
      };

      const revokeReq = https.request(options, (response) => {
        response.setEncoding("utf8");
        response.on("data", (d) => console.log("Revoked: ", d));
        response.on("end", resolve);
      });

      revokeReq.on("error", (error) => {
        console.error("Revoke error:", error);
        reject(error);
      });

      revokeReq.write(postData);
      revokeReq.end();
    });
  };

  Promise.all([
    access_token ? revokeToken(access_token) : Promise.resolve(),
    refresh_token ? revokeToken(refresh_token) : Promise.resolve()
  ])
  .then(() => {
    req.user = null;
  })
  .catch((err) => {
    throw new ApiError(401, "Invalid refresh token")
  });
});

export const getUserDetails = AsyncHandler(async (req, res) => {
  if(!req.user)throw new ApiError(401,"User not authenticated");
  const user=await User.findById(req.user._id).select("email name")
  if(!user)throw new ApiError(500, "User not found");
  return res.status(200).json(
    new ApiResponse(200,user,"User fetched successfully")
  )
});