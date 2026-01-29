/**
 * Demo Timeout Pattern
 */

import { ApiService } from "./services/ApiService.js";

async function demoTimeoutPattern() {
    console.log("=".repeat(80));
    console.log("DEMO: TIMEOUT PATTERN");
    console.log("=".repeat(80));
    console.log("\nTimeout tự động hủy request nếu mất quá nhiều thời gian\n");

    const apiService = new ApiService();

    // Test với các timeout khác nhau
    const timeouts = [1000, 2000, 3000, 5000];

    for (const timeout of timeouts) {
        console.log("=".repeat(80));
        console.log(`Testing with ${timeout}ms timeout`);
        console.log("=".repeat(80));

        const startTime = Date.now();

        try {
            const result = await apiService.callApiWithTimeout(timeout);
            const duration = Date.now() - startTime;

            console.log(`✅ Success!`);
            console.log(`Result: ${JSON.stringify(result)}`);
            console.log(`Duration: ${duration}ms`);
        } catch (error) {
            const duration = Date.now() - startTime;

            console.log(`❌ Failed!`);
            console.log(`Error: ${error.message}`);
            console.log(`Duration: ${duration}ms`);
        }

        console.log("\n");

        // Chờ một chút trước khi test tiếp
        await sleep(500);
    }

    // Hiển thị thống kê
    console.log("=".repeat(80));
    console.log("STATISTICS");
    console.log("=".repeat(80));
    const stats = apiService.getServiceStats();
    console.log(stats);
}

function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

// Chạy demo
demoTimeoutPattern().catch(console.error);
