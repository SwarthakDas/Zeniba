import { google } from "googleapis";
import crypto from "crypto";
import https from "https";
import {asyncHandler} from "../utils/asyncHandler.js";

const oauth2Client = new google.auth.OAuth2(
  process.env.AUTH_CLIENT_ID,
  process.env.AUTH_CLIENT_SECRET,
  process.env.AUTH_REDIRECT_URL
);

let userCredential = null;

const SCOPES = [
  'https://www.googleapis.com/auth/userinfo.email',
  'https://www.googleapis.com/auth/userinfo.profile'
];

export const googleAuthRedirect = asyncHandler(async (req, res) => {
  const state = crypto.randomBytes(32).toString("hex");
  req.session.state = state;

  const authUrl = oauth2Client.generateAuthUrl({
    access_type: "offline",
    scope: SCOPES,
    include_granted_scopes: true,
    state
  });

  res.redirect(authUrl);
});

export const googleAuthCallback = asyncHandler(async (req, res) => {
  const { code, state } = req.query;

  if (state !== req.session.state) {
    return res.status(403).send("State mismatch, possible CSRF");
  }

  const { tokens } = await oauth2Client.getToken(code);
  oauth2Client.setCredentials(tokens);
  req.app.locals.userCredential = tokens;


  const oauth2 = google.oauth2({ version: 'v2', auth: oauth2Client });
  const { data } = await oauth2.userinfo.get();

  console.log("User Info:");
  console.log("Name:", data.name);
  console.log("Email:", data.email);

  res.json({
    message: "Authentication successful",
    tokens
  });
});

export const revokeToken = asyncHandler((req, res) => {
  if (!userCredential?.access_token) {
    return res.status(400).send("No active token to revoke");
  }

  const postData = `token=${userCredential.access_token}`;
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
    response.on("data", (d) => {
      console.log("Revoked: ", d);
    });
    response.on("end", () => res.send("Token revoked"));
  });

  revokeReq.on("error", (error) => {
    console.error("Revoke error:", error);
    res.status(500).send("Failed to revoke token");
  });

  revokeReq.write(postData);
  revokeReq.end();
});

export const getUserDetails = async (req, res) => {
  const access_token  = req.app.locals.userCredential.access_token;

  if (!access_token) {
    return res.status(401).json({ message: "Missing access token" });
  }

  try {
    oauth2Client.setCredentials({ access_token });
    const oauth2 = google.oauth2({ version: "v2", auth: oauth2Client });
    const { data } = await oauth2.userinfo.get();

    res.json({
      name: data.name,
      email: data.email,
      picture: data.picture,
    });
  } catch (err) {
    console.error("User info fetch failed:", err);
    res.status(500).json({ message: "Failed to fetch user info" });
  }
};
//   const { access_token } = req.body;

//   if (!access_token) {
//     return res.status(401).json({ message: "Missing access token" });
//   }

//   try {
//     oauth2Client.setCredentials({ access_token });
//     const oauth2 = google.oauth2({ version: "v2", auth: oauth2Client });
//     const { data } = await oauth2.userinfo.get();

//     res.json({
//       name: data.name,
//       email: data.email,
//       picture: data.picture,
//     });
//   } catch (err) {
//     console.error("User info fetch failed:", err);
//     res.status(500).json({ message: "Failed to fetch user info" });
//   }
// };