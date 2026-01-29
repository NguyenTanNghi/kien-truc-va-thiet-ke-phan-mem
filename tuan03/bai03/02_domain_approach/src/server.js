/**
 * Server Entry Point
 * Domain Approach (Vertical Slice Architecture)
 */

import express from "express";
import { authRoutes } from "./domains/auth/index.js";
import { userRoutes } from "./domains/users/index.js";
import { errorHandler } from "./shared/middlewares/errorHandler.js";

const app = express();
const PORT = 3001;

// Middlewares
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Logging middleware
app.use((req, res, next) => {
    console.log(`[${new Date().toISOString()}] ${req.method} ${req.url}`);
    next();
});

// Domain routes - Mỗi domain tự quản lý routes của mình
app.use("/api/auth", authRoutes);
app.use("/api/users", userRoutes);

// Health check
app.get("/", (req, res) => {
    res.json({
        message: "Domain Approach API",
        architecture: "Vertical Slice / Domain-Driven Design",
        structure: "Tổ chức theo domain/business features (auth, users...)",
        domains: [
            {
                name: "auth",
                description: "Authentication domain - quản lý register, login",
                endpoints: ["POST /api/auth/register", "POST /api/auth/login"],
            },
            {
                name: "users",
                description: "Users domain - quản lý user profile",
                endpoints: [
                    "GET /api/users/profile (protected)",
                    "PUT /api/users/profile (protected)",
                    "GET /api/users (protected)",
                ],
            },
        ],
    });
});

// Error handler (phải đặt cuối cùng)
app.use(errorHandler);

// Start server
app.listen(PORT, () => {
    console.log("\n" + "=".repeat(60));
    console.log("🚀 DOMAIN APPROACH - Vertical Slice Architecture");
    console.log("=".repeat(60));
    console.log(`Server running on http://localhost:${PORT}`);
    console.log(`Health check: http://localhost:${PORT}`);
    console.log("\n📦 Domains loaded:");
    console.log("  - auth: Authentication & Authorization");
    console.log("  - users: User Management");
    console.log("=".repeat(60) + "\n");
});
