/**
 * User Controller
 * Xử lý HTTP requests liên quan đến User
 */

import { userService } from "../services/userService.js";

class UserController {
    // GET /api/users/profile
    async getProfile(req, res, next) {
        try {
            const userId = req.user.id; // Lấy từ middleware auth
            const user = userService.getUserProfile(userId);

            res.json({
                success: true,
                data: user,
            });
        } catch (error) {
            next(error);
        }
    }

    // GET /api/users
    async getAllUsers(req, res, next) {
        try {
            const users = userService.getAllUsers();

            res.json({
                success: true,
                data: users,
            });
        } catch (error) {
            next(error);
        }
    }

    // PUT /api/users/profile
    async updateProfile(req, res, next) {
        try {
            const userId = req.user.id;
            const updatedUser = userService.updateUserProfile(userId, req.body);

            res.json({
                success: true,
                message: "Cập nhật profile thành công",
                data: updatedUser,
            });
        } catch (error) {
            next(error);
        }
    }
}

export const userController = new UserController();
