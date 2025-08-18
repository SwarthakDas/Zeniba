import mongoose from "mongoose";
import { app } from "./app.js";
import process from "node:process";
import dotenv from "dotenv";
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

connectDB()
.then(()=>{
    app.listen(process.env.PORT,()=>console.log("Server running on PORT ",process.env.PORT))
})
.catch((err)=>console.error("Failed to start server ",err))