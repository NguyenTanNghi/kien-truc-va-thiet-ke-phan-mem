/**
 * Auth Middleware
 * Shared middleware cho authentication
 */

import { authService } from "../../domains/auth/auth.service.js";

export const authMiddleware = async (req, res, next) => {
    try {
        const authHeader = req.headers.authorization;

        if (!authHeader || !authHeader.startsWith("Bearer ")) {
            return res.status(401).json({
                success: false,
                message: "Token không được cung cấp",
            });
        }

        const token = authHeader.substring(7);
        const decoded = authService.verifyUserToken(token);

        req.user = decoded;
        next();
    } catch (error) {
        res.status(401).json({
            success: false,
            message: "Token không hợp lệ hoặc đã hết hạn",
        });
    }
};
