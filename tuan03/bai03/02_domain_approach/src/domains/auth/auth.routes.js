/**
 * Auth Routes
 * Thuộc Auth Domain - Định nghĩa routes
 */

import express from "express";
import { authController } from "./auth.controller.js";

const router = express.Router();

// Tất cả routes của Auth domain
router.post("/register", (req, res, next) =>
    authController.register(req, res, next),
);

router.post("/login", (req, res, next) => authController.login(req, res, next));

export default router;
