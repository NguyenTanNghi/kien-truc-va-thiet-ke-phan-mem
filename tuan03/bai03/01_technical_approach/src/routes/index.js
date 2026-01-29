/**
 * Routes Configuration
 * Định nghĩa tất cả routes của application
 */

import express from "express";
import { authController } from "../controllers/authController.js";
import { userController } from "../controllers/userController.js";
import { authMiddleware } from "../middlewares/authMiddleware.js";

const router = express.Router();

// Auth routes
router.post("/auth/register", (req, res, next) =>
    authController.register(req, res, next),
);
router.post("/auth/login", (req, res, next) =>
    authController.login(req, res, next),
);

// User routes (protected)
router.get("/users/profile", authMiddleware, (req, res, next) =>
    userController.getProfile(req, res, next),
);
router.put("/users/profile", authMiddleware, (req, res, next) =>
    userController.updateProfile(req, res, next),
);
router.get("/users", authMiddleware, (req, res, next) =>
    userController.getAllUsers(req, res, next),
);

export default router;
