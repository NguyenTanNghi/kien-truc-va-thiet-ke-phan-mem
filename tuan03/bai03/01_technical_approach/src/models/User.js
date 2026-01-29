/**
 * User Model
 * Định nghĩa cấu trúc dữ liệu User
 */

export class User {
    constructor(data) {
        this.id = data.id;
        this.username = data.username;
        this.email = data.email;
        this.password = data.password;
        this.createdAt = data.createdAt || new Date();
    }

    // Remove password khi trả về client
    toJSON() {
        const { password, ...userWithoutPassword } = this;
        return userWithoutPassword;
    }
}
