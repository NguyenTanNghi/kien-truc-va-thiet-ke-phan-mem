/**
 * JWT Utils
 * Shared utility cho JWT operations
 */

import jwt from "jsonwebtoken";

const JWT_SECRET = "your-secret-key-change-in-production";
const JWT_EXPIRES_IN = "7d";

export function generateToken(user) {
    return jwt.sign(
        {
            id: user.id,
            email: user.email,
            username: user.username,
        },
        JWT_SECRET,
        { expiresIn: JWT_EXPIRES_IN },
    );
}

export function verifyToken(token) {
    try {
        return jwt.verify(token, JWT_SECRET);
    } catch (error) {
        throw new Error("Token không hợp lệ hoặc đã hết hạn");
    }
}
