/**
 * Demo Circuit Breaker Pattern
 */

import { ApiService } from "./services/ApiService.js";

async function demoCircuitBreaker() {
    console.log("=".repeat(80));
    console.log("DEMO: CIRCUIT BREAKER PATTERN");
    console.log("=".repeat(80));
    console.log(
        '\nCircuit Breaker tự động "mở mạch" khi phát hiện nhiều lỗi liên tiếp',
    );
    console.log('và "đóng mạch" khi service hoạt động trở lại bình thường.\n');

    const apiService = new ApiService();

    // Gửi nhiều requests để kích hoạt circuit breaker
    console.log("📤 Sending 15 requests to unstable service...\n");

    for (let i = 1; i <= 15; i++) {
        try {
            const result = await apiService.getDataWithCircuitBreaker(i);
            console.log(`✅ Success: ${JSON.stringify(result)}`);
        } catch (error) {
            console.log(`❌ Error: ${error.message}`);
        }

        // Hiển thị trạng thái circuit breaker
        const state = apiService.getCircuitBreakerState();
        console.log(
            `Circuit State: ${state.state} (Failures: ${state.failureCount})`,
        );

        // Chờ một chút giữa các requests
        await sleep(1000);

        console.log("-".repeat(80));
    }

    // Hiển thị thống kê
    console.log("\n" + "=".repeat(80));
    console.log("STATISTICS");
    console.log("=".repeat(80));
    const stats = apiService.getServiceStats();
    console.log(stats);

    const finalState = apiService.getCircuitBreakerState();
    console.log("\nFinal Circuit Breaker State:", finalState);
}

function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

// Chạy demo
demoCircuitBreaker().catch(console.error);
