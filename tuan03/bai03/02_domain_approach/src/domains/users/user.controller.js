/**
 * User Controller
 * Thuộc Users Domain - HTTP handlers
 */

import { userService } from "./user.service.js";

class UserController {
    async getProfile(req, res, next) {
        try {
            const userId = req.user.id;
            const user = userService.getUserProfile(userId);

            res.json({
                success: true,
                data: user,
            });
        } catch (error) {
            next(error);
        }
    }

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
