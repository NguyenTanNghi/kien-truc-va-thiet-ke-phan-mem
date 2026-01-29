/**
 * Auth Service
 * Thuộc Auth Domain - Business logic
 */

import bcrypt from "bcryptjs";
import { generateToken, verifyToken } from "../../shared/utils/jwtUtils.js";
import { userRepository } from "../users/user.repository.js";

class AuthService {
    async register(userData) {
        const { username, email, password } = userData;

        // Validate input
        if (!username || !email || !password) {
            throw new Error("Username, email và password là bắt buộc");
        }

        if (password.length < 6) {
            throw new Error("Password phải có ít nhất 6 ký tự");
        }

        // Kiểm tra email đã tồn tại
        const existingUser = userRepository.findByEmail(email);
        if (existingUser) {
            throw new Error("Email đã được sử dụng");
        }

        // Kiểm tra username đã tồn tại
        const existingUsername = userRepository.findByUsername(username);
        if (existingUsername) {
            throw new Error("Username đã được sử dụng");
        }

        // Hash password
        const hashedPassword = await bcrypt.hash(password, 10);

        // Tạo user mới
        const user = userRepository.create({
            username,
            email,
            password: hashedPassword,
        });

        // Tạo JWT token
        const token = generateToken(user);

        return {
            user: user.toJSON(),
            token,
        };
    }

    async login(credentials) {
        const { email, password } = credentials;

        // Validate input
        if (!email || !password) {
            throw new Error("Email và password là bắt buộc");
        }

        // Tìm user theo email
        const user = userRepository.findByEmail(email);
        if (!user) {
            throw new Error("Email hoặc password không đúng");
        }

        // Verify password
        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            throw new Error("Email hoặc password không đúng");
        }

        // Tạo JWT token
        const token = generateToken(user);

        return {
            user: user.toJSON(),
            token,
        };
    }

    verifyUserToken(token) {
        return verifyToken(token);
    }
}

export const authService = new AuthService();
