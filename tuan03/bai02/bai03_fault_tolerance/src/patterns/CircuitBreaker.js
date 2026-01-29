/**
 * Circuit Breaker Pattern
 *
 * States:
 * - CLOSED: Hoạt động bình thường, cho phép tất cả requests
 * - OPEN: Từ chối tất cả requests, trả về lỗi ngay lập tức
 * - HALF_OPEN: Cho phép một số requests để kiểm tra service đã hoạt động chưa
 */

export class CircuitBreaker {
    constructor(options = {}) {
        // Ngưỡng số lỗi liên tiếp trước khi mở circuit
        this.failureThreshold = options.failureThreshold || 5;

        // Thời gian chờ trước khi chuyển từ OPEN sang HALF_OPEN (ms)
        this.resetTimeout = options.resetTimeout || 60000; // 60 giây

        // Số request thử trong trạng thái HALF_OPEN
        this.halfOpenRequests = options.halfOpenRequests || 3;

        // Trạng thái hiện tại
        this.state = "CLOSED";

        // Đếm số lỗi liên tiếp
        this.failureCount = 0;

        // Đếm số request thành công liên tiếp
        this.successCount = 0;

        // Thời điểm mở circuit
        this.nextAttempt = Date.now();

        // Đếm số request trong trạng thái HALF_OPEN
        this.halfOpenAttempts = 0;
    }

    async execute(fn, fallback = null) {
        // Kiểm tra trạng thái trước khi thực thi
        if (this.state === "OPEN") {
            // Kiểm tra có đến lúc thử lại chưa
            if (Date.now() < this.nextAttempt) {
                console.log("🔴 Circuit is OPEN - Request rejected");

                // Nếu có fallback, trả về fallback
                if (fallback) {
                    return fallback();
                }

                throw new Error("Circuit breaker is OPEN");
            }

            // Chuyển sang HALF_OPEN để thử lại
            this.state = "HALF_OPEN";
            this.halfOpenAttempts = 0;
            console.log("🟡 Circuit is HALF_OPEN - Testing service...");
        }

        try {
            // Thực thi function
            const result = await fn();

            // Thành công - reset failure count
            this.onSuccess();

            return result;
        } catch (error) {
            // Thất bại - tăng failure count
            this.onFailure();

            throw error;
        }
    }

    onSuccess() {
        this.failureCount = 0;

        if (this.state === "HALF_OPEN") {
            this.successCount++;
            this.halfOpenAttempts++;

            // Nếu đủ số request thành công, đóng circuit
            if (this.successCount >= this.halfOpenRequests) {
                this.close();
            }
        }
    }

    onFailure() {
        this.failureCount++;
        this.successCount = 0;

        if (this.state === "HALF_OPEN") {
            // Nếu fail trong HALF_OPEN, mở lại circuit
            this.open();
        } else if (this.failureCount >= this.failureThreshold) {
            // Nếu đạt ngưỡng lỗi, mở circuit
            this.open();
        }
    }

    open() {
        this.state = "OPEN";
        this.nextAttempt = Date.now() + this.resetTimeout;
        console.log(
            `🔴 Circuit OPENED - Will retry at ${new Date(this.nextAttempt).toISOString()}`,
        );
    }

    close() {
        this.state = "CLOSED";
        this.failureCount = 0;
        this.successCount = 0;
        console.log("🟢 Circuit CLOSED - Service is healthy");
    }

    getState() {
        return {
            state: this.state,
            failureCount: this.failureCount,
            successCount: this.successCount,
            nextAttempt:
                this.state === "OPEN" ? new Date(this.nextAttempt) : null,
        };
    }
}
