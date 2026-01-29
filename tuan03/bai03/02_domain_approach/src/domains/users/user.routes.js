/**
 * User Routes
 * Thuộc Users Domain - Định nghĩa routes
 */

import express from "express";
import { userController } from "./user.controller.js";
import { authMiddleware } from "../../shared/middlewares/authMiddleware.js";

const router = express.Router();

// Tất cả routes của Users domain
router.get("/profile", authMiddleware, (req, res, next) =>
    userController.getProfile(req, res, next),
);

router.put("/profile", authMiddleware, (req, res, next) =>
    userController.updateProfile(req, res, next),
);

router.get("/", authMiddleware, (req, res, next) =>
    userController.getAllUsers(req, res, next),
);

export default router;
