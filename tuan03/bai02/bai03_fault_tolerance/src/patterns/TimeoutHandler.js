/**
 * Timeout Pattern
 *
 * Đặt giới hạn thời gian cho các operation để tránh treo hệ thống
 */

export class TimeoutHandler {
    constructor(options = {}) {
        // Thời gian timeout mặc định (ms)
        this.defaultTimeout = options.defaultTimeout || 5000;
    }

    async execute(fn, timeout = this.defaultTimeout) {
        return Promise.race([fn(), this.createTimeoutPromise(timeout)]);
    }

    createTimeoutPromise(timeout) {
        return new Promise((_, reject) => {
            setTimeout(() => {
                reject(new Error(`Operation timed out after ${timeout}ms`));
            }, timeout);
        });
    }

    // Timeout với cleanup function
    async executeWithCleanup(fn, cleanup, timeout = this.defaultTimeout) {
        let timeoutId;

        try {
            const result = await Promise.race([
                fn(),
                new Promise((_, reject) => {
                    timeoutId = setTimeout(() => {
                        reject(
                            new Error(`Operation timed out after ${timeout}ms`),
                        );
                    }, timeout);
                }),
            ]);

            // Clear timeout nếu thành công
            if (timeoutId) {
                clearTimeout(timeoutId);
            }

            return result;
        } catch (error) {
            // Clear timeout
            if (timeoutId) {
                clearTimeout(timeoutId);
            }

            // Chạy cleanup function
            if (cleanup) {
                try {
                    await cleanup();
                } catch (cleanupError) {
                    console.error("Cleanup error:", cleanupError);
                }
            }

            throw error;
        }
    }

    // Timeout với retry
    async executeWithRetry(fn, maxRetries = 3, timeout = this.defaultTimeout) {
        let lastError;

        for (let attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                console.log(
                    `⏱️  Attempt ${attempt}/${maxRetries} with ${timeout}ms timeout`,
                );

                const result = await this.execute(fn, timeout);

                if (attempt > 1) {
                    console.log(`✅ Success after ${attempt} attempts`);
                }

                return result;
            } catch (error) {
                lastError = error;
                console.log(`❌ Attempt ${attempt} failed: ${error.message}`);

                if (attempt < maxRetries) {
                    // Tăng timeout cho lần retry tiếp theo
                    timeout = Math.min(timeout * 1.5, 30000);
                    console.log(
                        `⏱️  Increasing timeout to ${timeout}ms for next attempt`,
                    );

                    // Chờ một chút trước khi retry
                    await new Promise((resolve) => setTimeout(resolve, 1000));
                }
            }
        }

        console.log(`❌ All ${maxRetries} attempts failed`);
        throw lastError;
    }

    // Timeout với fallback value
    async executeWithFallback(
        fn,
        fallbackValue,
        timeout = this.defaultTimeout,
    ) {
        try {
            return await this.execute(fn, timeout);
        } catch (error) {
            console.log(`⚠️  Timeout occurred, returning fallback value`);
            return fallbackValue;
        }
    }

    // Kiểm tra timeout của nhiều operations song song
    async executeAll(operations, timeout = this.defaultTimeout) {
        const wrappedOperations = operations.map((op) =>
            this.execute(op, timeout),
        );
        return Promise.all(wrappedOperations);
    }

    // Đợi operation nhanh nhất (race condition)
    async executeRace(operations, timeout = this.defaultTimeout) {
        const wrappedOperations = operations.map((op) =>
            this.execute(op, timeout),
        );
        return Promise.race(wrappedOperations);
    }
}
