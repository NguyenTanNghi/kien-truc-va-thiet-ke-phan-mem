/**
 * User Repository
 * Xử lý tất cả data access liên quan đến User
 * (Trong thực tế sẽ kết nối database, đây dùng in-memory)
 */

import { User } from "../models/User.js";

class UserRepository {
    constructor() {
        // Giả lập database với in-memory storage
        this.users = [];
        this.nextId = 1;
    }

    // Tạo user mới
    create(userData) {
        const user = new User({
            id: this.nextId++,
            ...userData,
        });
        this.users.push(user);
        return user;
    }

    // Tìm user theo email
    findByEmail(email) {
        return this.users.find((user) => user.email === email);
    }

    // Tìm user theo id
    findById(id) {
        return this.users.find((user) => user.id === id);
    }

    // Tìm user theo username
    findByUsername(username) {
        return this.users.find((user) => user.username === username);
    }

    // Lấy tất cả users
    findAll() {
        return this.users;
    }

    // Update user
    update(id, userData) {
        const index = this.users.findIndex((user) => user.id === id);
        if (index !== -1) {
            this.users[index] = { ...this.users[index], ...userData };
            return this.users[index];
        }
        return null;
    }

    // Delete user
    delete(id) {
        const index = this.users.findIndex((user) => user.id === id);
        if (index !== -1) {
            return this.users.splice(index, 1)[0];
        }
        return null;
    }
}

// Singleton instance
export const userRepository = new UserRepository();
