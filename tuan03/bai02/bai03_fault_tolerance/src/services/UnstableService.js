/**
 * Mock Service không ổn định
 * Mô phỏng các tình huống lỗi: network error, timeout, server error
 */

export class UnstableService {
    constructor(options = {}) {
        // Tỷ lệ thất bại (0-1)
        this.failureRate = options.failureRate || 0.5;

        // Độ trễ tối thiểu và tối đa (ms)
        this.minDelay = options.minDelay || 100;
        this.maxDelay = options.maxDelay || 3000;

        // Đếm số request
        this.requestCount = 0;
        this.successCount = 0;
        this.failureCount = 0;
    }

    async getData(id) {
        this.requestCount++;

        // Random delay để mô phỏng network latency
        const delay = this.getRandomDelay();
        await this.sleep(delay);

        // Random failure
        if (Math.random() < this.failureRate) {
            this.failureCount++;

            // Random error type
            const errorType = this.getRandomErrorType();
            throw new Error(errorType);
        }

        // Success
        this.successCount++;
        return {
            id,
            data: `Data for ID ${id}`,
            timestamp: new Date().toISOString(),
            delay: `${delay}ms`,
        };
    }

    async processOrder(orderId) {
        this.requestCount++;

        const delay = this.getRandomDelay();
        await this.sleep(delay);

        if (Math.random() < this.failureRate) {
            this.failureCount++;
            throw new Error("ECONNREFUSED: Service unavailable");
        }

        this.successCount++;
        return {
            orderId,
            status: "processed",
            timestamp: new Date().toISOString(),
        };
    }

    async callExternalAPI() {
        this.requestCount++;

        const delay = this.getRandomDelay();
        await this.sleep(delay);

        if (Math.random() < this.failureRate) {
            this.failureCount++;
            const errors = [
                "ETIMEDOUT: Request timeout",
                "ENOTFOUND: DNS lookup failed",
                "ECONNREFUSED: Connection refused",
                "Network error occurred",
            ];
            throw new Error(errors[Math.floor(Math.random() * errors.length)]);
        }

        this.successCount++;
        return {
            result: "External API response",
            timestamp: new Date().toISOString(),
        };
    }

    getRandomDelay() {
        return Math.floor(
            Math.random() * (this.maxDelay - this.minDelay) + this.minDelay,
        );
    }

    getRandomErrorType() {
        const errors = [
            "ECONNREFUSED: Connection refused",
            "ETIMEDOUT: Request timeout",
            "ENOTFOUND: DNS lookup failed",
            "Network error occurred",
            "Service temporarily unavailable",
            "503 Service Unavailable",
        ];
        return errors[Math.floor(Math.random() * errors.length)];
    }

    sleep(ms) {
        return new Promise((resolve) => setTimeout(resolve, ms));
    }

    getStats() {
        const successRate =
            this.requestCount > 0
                ? ((this.successCount / this.requestCount) * 100).toFixed(2)
                : 0;

        return {
            totalRequests: this.requestCount,
            successCount: this.successCount,
            failureCount: this.failureCount,
            successRate: `${successRate}%`,
        };
    }

    reset() {
        this.requestCount = 0;
        this.successCount = 0;
        this.failureCount = 0;
    }
}
