import mongoose from "mongoose";
import http from "node:http"
import { app } from "./app.js";
import { availableParallelism } from "node:os";
import cluster from 'node:cluster';
import process from "node:process";
import dotenv from "dotenv";
dotenv.config()

const cpu=availableParallelism()

const connectDB=async()=>{
    try {
        const res=await mongoose.connect(`${process.env.MONGO_URI}`)
        console.log("DB connected: ",res.connection.host)
    } catch (error) {
        console.error("Failed to connect Database ",error)
        process.exit(1)
    }
}

if (cluster.isPrimary) {
  console.log(`Primary ${process.pid} is running`);
  for (let i = 0; i < cpu; i++) {
    cluster.fork();
  }
  cluster.on('exit', (worker, code, signal) => {
    console.log(`worker ${worker.process.pid} died`);
  });
} else{
    connectDB()
    .then(()=>{
        const server=http.createServer(app)
        server.listen(process.env.PORT,()=>console.log("Server running on PORT ",process.env.PORT))
    })
    .catch((err)=>console.error("Failed to start server ",err))
}