/**
 * Auth Middleware
 * Xác thực JWT token
 */

import { authService } from "../services/authService.js";

export const authMiddleware = async (req, res, next) => {
    try {
        // Lấy token từ header
        const authHeader = req.headers.authorization;

        if (!authHeader || !authHeader.startsWith("Bearer ")) {
            return res.status(401).json({
                success: false,
                message: "Token không được cung cấp",
            });
        }

        const token = authHeader.substring(7); // Remove 'Bearer '

        // Verify token
        const decoded = authService.verifyToken(token);

        // Gắn user info vào request
        req.user = decoded;

        next();
    } catch (error) {
        res.status(401).json({
            success: false,
            message: "Token không hợp lệ hoặc đã hết hạn",
        });
    }
};
