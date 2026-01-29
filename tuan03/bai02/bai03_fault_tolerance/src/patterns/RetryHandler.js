/**
 * Retry Pattern với Exponential Backoff
 *
 * Tự động thử lại khi gặp lỗi với thời gian chờ tăng dần
 */

export class RetryHandler {
    constructor(options = {}) {
        // Số lần retry tối đa
        this.maxRetries = options.maxRetries || 3;

        // Thời gian chờ ban đầu (ms)
        this.initialDelay = options.initialDelay || 1000;

        // Hệ số nhân cho exponential backoff
        this.backoffMultiplier = options.backoffMultiplier || 2;

        // Thời gian chờ tối đa (ms)
        this.maxDelay = options.maxDelay || 30000;
    }

    async execute(fn, retryableErrors = []) {
        let lastError;
        let attempt = 0;

        while (attempt <= this.maxRetries) {
            try {
                // Nếu không phải lần đầu, log retry attempt
                if (attempt > 0) {
                    console.log(
                        `🔄 Retry attempt ${attempt}/${this.maxRetries}`,
                    );
                }

                // Thực thi function
                const result = await fn();

                // Thành công
                if (attempt > 0) {
                    console.log(`✅ Success after ${attempt} retries`);
                }

                return result;
            } catch (error) {
                lastError = error;
                attempt++;

                // Kiểm tra xem có nên retry không
                if (!this.shouldRetry(error, attempt, retryableErrors)) {
                    throw error;
                }

                // Tính thời gian chờ với exponential backoff
                const delay = this.calculateDelay(attempt);

                console.log(`❌ Attempt ${attempt} failed: ${error.message}`);
                console.log(`⏱️  Waiting ${delay}ms before retry...`);

                // Chờ trước khi retry
                await this.sleep(delay);
            }
        }

        // Đã hết số lần retry
        console.log(`❌ All ${this.maxRetries} retries failed`);
        throw lastError;
    }

    shouldRetry(error, attempt, retryableErrors) {
        // Nếu đã hết số lần retry
        if (attempt > this.maxRetries) {
            return false;
        }

        // Nếu có danh sách lỗi có thể retry
        if (retryableErrors.length > 0) {
            return retryableErrors.some(
                (errorType) =>
                    error.message.includes(errorType) ||
                    error.code === errorType,
            );
        }

        // Mặc định retry cho network errors
        return (
            error.code === "ECONNREFUSED" ||
            error.code === "ETIMEDOUT" ||
            error.code === "ENOTFOUND" ||
            error.message.includes("timeout") ||
            error.message.includes("network")
        );
    }

    calculateDelay(attempt) {
        // Exponential backoff: initialDelay * (backoffMultiplier ^ (attempt - 1))
        const delay =
            this.initialDelay * Math.pow(this.backoffMultiplier, attempt - 1);

        // Thêm jitter (random) để tránh thundering herd
        const jitter = Math.random() * 1000;

        // Giới hạn delay tối đa
        return Math.min(delay + jitter, this.maxDelay);
    }

    sleep(ms) {
        return new Promise((resolve) => setTimeout(resolve, ms));
    }

    // Retry với custom logic
    async executeWithCustomRetry(fn, shouldRetryFn) {
        let lastError;
        let attempt = 0;

        while (attempt <= this.maxRetries) {
            try {
                if (attempt > 0) {
                    console.log(
                        `🔄 Retry attempt ${attempt}/${this.maxRetries}`,
                    );
                }

                const result = await fn();

                if (attempt > 0) {
                    console.log(`✅ Success after ${attempt} retries`);
                }

                return result;
            } catch (error) {
                lastError = error;
                attempt++;

                if (!shouldRetryFn(error, attempt)) {
                    throw error;
                }

                const delay = this.calculateDelay(attempt);
                console.log(`❌ Attempt ${attempt} failed: ${error.message}`);
                console.log(`⏱️  Waiting ${delay}ms before retry...`);

                await this.sleep(delay);
            }
        }

        console.log(`❌ All ${this.maxRetries} retries failed`);
        throw lastError;
    }
}
