/**
 * Demo Retry Pattern với Exponential Backoff
 */

import { ApiService } from "./services/ApiService.js";

async function demoRetryPattern() {
    console.log("=".repeat(80));
    console.log("DEMO: RETRY PATTERN WITH EXPONENTIAL BACKOFF");
    console.log("=".repeat(80));
    console.log(
        "\nRetry tự động thử lại khi gặp lỗi với thời gian chờ tăng dần",
    );
    console.log("(Exponential Backoff: 1s -> 2s -> 4s ...)\n");

    const apiService = new ApiService();

    // Test retry với 5 orders
    console.log("📤 Processing 5 orders with retry mechanism...\n");

    for (let orderId = 1; orderId <= 5; orderId++) {
        console.log("=".repeat(80));
        console.log(`Order ${orderId}/5`);
        console.log("=".repeat(80));

        const startTime = Date.now();

        try {
            const result = await apiService.processOrderWithRetry(orderId);
            const duration = Date.now() - startTime;

            console.log(`\n✅ Order ${orderId} processed successfully!`);
            console.log(`Result: ${JSON.stringify(result)}`);
            console.log(`Total time: ${duration}ms`);
        } catch (error) {
            const duration = Date.now() - startTime;

            console.log(`\n❌ Order ${orderId} failed after all retries!`);
            console.log(`Error: ${error.message}`);
            console.log(`Total time: ${duration}ms`);
        }

        console.log("\n");
    }

    // Hiển thị thống kê
    console.log("=".repeat(80));
    console.log("STATISTICS");
    console.log("=".repeat(80));
    const stats = apiService.getServiceStats();
    console.log(stats);
}

// Chạy demo
demoRetryPattern().catch(console.error);
