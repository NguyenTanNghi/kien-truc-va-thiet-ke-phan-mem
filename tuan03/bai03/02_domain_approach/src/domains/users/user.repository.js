/**
 * User Repository
 * Thuộc Users Domain - Xử lý data access
 */

import { User } from "./user.model.js";

class UserRepository {
    constructor() {
        this.users = [];
        this.nextId = 1;
    }

    create(userData) {
        const user = new User({
            id: this.nextId++,
            ...userData,
        });
        this.users.push(user);
        return user;
    }

    findByEmail(email) {
        return this.users.find((user) => user.email === email);
    }

    findById(id) {
        return this.users.find((user) => user.id === id);
    }

    findByUsername(username) {
        return this.users.find((user) => user.username === username);
    }

    findAll() {
        return this.users;
    }

    update(id, userData) {
        const index = this.users.findIndex((user) => user.id === id);
        if (index !== -1) {
            this.users[index] = { ...this.users[index], ...userData };
            return this.users[index];
        }
        return null;
    }

    delete(id) {
        const index = this.users.findIndex((user) => user.id === id);
        if (index !== -1) {
            return this.users.splice(index, 1)[0];
        }
        return null;
    }
}

export const userRepository = new UserRepository();
