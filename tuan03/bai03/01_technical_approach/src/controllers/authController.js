/**
 * Auth Controller
 * Xử lý HTTP requests liên quan đến authentication
 */

import { authService } from "../services/authService.js";

class AuthController {
    // POST /api/auth/register
    async register(req, res, next) {
        try {
            const result = await authService.register(req.body);

            res.status(201).json({
                success: true,
                message: "Đăng ký thành công",
                data: result,
            });
        } catch (error) {
            next(error);
        }
    }

    // POST /api/auth/login
    async login(req, res, next) {
        try {
            const result = await authService.login(req.body);

            res.json({
                success: true,
                message: "Đăng nhập thành công",
                data: result,
            });
        } catch (error) {
            next(error);
        }
    }
}

export const authController = new AuthController();
