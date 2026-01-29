/**
 * Auth Controller
 * Thuộc Auth Domain - HTTP handlers
 */

import { authService } from "./auth.service.js";

class AuthController {
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
