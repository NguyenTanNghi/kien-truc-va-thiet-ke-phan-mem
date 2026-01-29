/**
 * User Service
 * Xử lý business logic liên quan đến User
 */

import { userRepository } from "../repositories/userRepository.js";

class UserService {
    // Lấy thông tin user theo ID
    getUserById(userId) {
        const user = userRepository.findById(userId);
        if (!user) {
            throw new Error("User không tồn tại");
        }
        return user.toJSON();
    }

    // Lấy profile của user hiện tại
    getUserProfile(userId) {
        return this.getUserById(userId);
    }

    // Lấy tất cả users
    getAllUsers() {
        const users = userRepository.findAll();
        return users.map((user) => user.toJSON());
    }

    // Update user profile
    updateUserProfile(userId, updateData) {
        const user = userRepository.findById(userId);
        if (!user) {
            throw new Error("User không tồn tại");
        }

        // Không cho phép update email hoặc password qua endpoint này
        const { email, password, ...safeUpdateData } = updateData;

        const updatedUser = userRepository.update(userId, safeUpdateData);
        return updatedUser.toJSON();
    }
}

export const userService = new UserService();
