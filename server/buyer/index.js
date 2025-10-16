import { app } from "./app.js";
import dotenv from "dotenv"
import connectDB from "./utils/db.js";
dotenv.config()

connectDB()
.then(()=>{
    app.listen(process.env.PORT,()=>console.log("Server running on PORT ",process.env.PORT))
})
.catch((err)=>console.error("Failed to start server ",err))