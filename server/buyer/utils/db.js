import mongoose from "mongoose";
import dotenv from "dotenv"

dotenv.config()
const connectDB=async()=>{
    try {
        const res=await mongoose.connect(`${process.env.MONGO_URI}`)
        console.log("DB connected: ",res.connection.host)
    } catch (error) {
        console.error("Failed to connect Database ",error)
        process.exit(1)
    }
}

export default connectDB