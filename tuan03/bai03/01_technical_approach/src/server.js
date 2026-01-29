/**
 * Server Entry Point
 * Technical Approach (Layered Architecture)
 */

import express from "express";
import routes from "./routes/index.js";
import { errorHandler } from "./middlewares/errorHandler.js";

const app = express();
const PORT = 3000;

// Middlewares
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Logging middleware
app.use((req, res, next) => {
    console.log(`[${new Date().toISOString()}] ${req.method} ${req.url}`);
    next();
});

// API routes
app.use("/api", routes);

// Health check
app.get("/", (req, res) => {
    res.json({
        message: "Technical Approach API",
        architecture:
            "Layered Architecture (Controllers -> Services -> Repositories)",
        endpoints: {
            register: "POST /api/auth/register",
            login: "POST /api/auth/login",
            profile: "GET /api/users/profile (protected)",
            updateProfile: "PUT /api/users/profile (protected)",
            users: "GET /api/users (protected)",
        },
    });
});

// Error handler (phải đặt cuối cùng)
app.use(errorHandler);

// Start server
app.listen(PORT, () => {
    console.log("\n" + "=".repeat(60));
    console.log("🚀 TECHNICAL APPROACH - Layered Architecture");
    console.log("=".repeat(60));
    console.log(`Server running on http://localhost:${PORT}`);
    console.log(`Health check: http://localhost:${PORT}`);
    console.log("=".repeat(60) + "\n");
});
