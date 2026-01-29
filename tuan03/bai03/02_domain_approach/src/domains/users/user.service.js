/**
 * User Service
 * Thuộc Users Domain - Business logic
 */

import { userRepository } from "./user.repository.js";

class UserService {
    getUserById(userId) {
        const user = userRepository.findById(userId);
        if (!user) {
            throw new Error("User không tồn tại");
        }
        return user.toJSON();
    }

    getUserProfile(userId) {
        return this.getUserById(userId);
    }

    getAllUsers() {
        const users = userRepository.findAll();
        return users.map((user) => user.toJSON());
    }

    updateUserProfile(userId, updateData) {
        const user = userRepository.findById(userId);
        if (!user) {
            throw new Error("User không tồn tại");
        }

        const { email, password, ...safeUpdateData } = updateData;
        const updatedUser = userRepository.update(userId, safeUpdateData);
        return updatedUser.toJSON();
    }
}

export const userService = new UserService();
