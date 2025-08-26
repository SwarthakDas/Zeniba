import mongoose from "mongoose";
import jwt from "jsonwebtoken"
import dotenv from "dotenv"
dotenv.config()

const UserSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
    unique: true,
  },
  pic: {
    type: String,
    default: "",
  },
}, { timestamps: true });

UserSchema.methods.generateAccessToken = function () {
    return jwt.sign(
        {
            _id: this._id.toString()
        },
        process.env.ACCESS_TOKEN_SECRET,
        {
            expiresIn: process.env.ACCESS_TOKEN_EXPIRY
        }
    );
};

UserSchema.methods.generateRefreshToken = function () {
    return jwt.sign(
        {
            _id: this._id.toString()
        },
        process.env.REFRESH_TOKEN_SECRET,
        {
            expiresIn: process.env.REFRESH_TOKEN_EXPIRY
        }
    );
};

const User = mongoose.model("User", UserSchema);
export default User;
