/**
 * Main Entry Point - Demo tất cả Fault Tolerance Patterns
 */

import { ApiService } from "./services/ApiService.js";

async function main() {
    console.log("╔" + "═".repeat(78) + "╗");
    console.log(
        "║" + " ".repeat(20) + "FAULT TOLERANCE DEMO" + " ".repeat(38) + "║",
    );
    console.log("╚" + "═".repeat(78) + "╝");
    console.log(
        "\n📚 Minh họa các kỹ thuật xử lý lỗi trong hệ thống phân tán:\n",
    );
    console.log("   1️⃣  Circuit Breaker - Ngăn chặn cascade failure");
    console.log(
        "   2️⃣  Retry Pattern - Tự động thử lại với exponential backoff",
    );
    console.log("   3️⃣  Timeout - Giới hạn thời gian chờ");
    console.log("   4️⃣  Robust Request - Kết hợp tất cả patterns\n");

    const apiService = new ApiService();

    // Demo 1: Circuit Breaker
    await demoSection("Circuit Breaker Pattern", async () => {
        console.log("Gửi 8 requests để test circuit breaker...\n");

        for (let i = 1; i <= 8; i++) {
            try {
                const result = await apiService.getDataWithCircuitBreaker(i);
                console.log(`  ✅ Request ${i}: Success`);
                if (result.fromCache) {
                    console.log(`     └─ Fallback data used (Circuit is OPEN)`);
                }
            } catch (error) {
                console.log(`  ❌ Request ${i}: ${error.message}`);
            }

            const state = apiService.getCircuitBreakerState();
            console.log(
                `     └─ Circuit: ${state.state} | Failures: ${state.failureCount}\n`,
            );

            await sleep(800);
        }
    });

    // Reset stats
    apiService.resetStats();
    await sleep(2000);

    // Demo 2: Retry Pattern
    await demoSection("Retry Pattern with Exponential Backoff", async () => {
        console.log("Process 3 orders với retry mechanism...\n");

        for (let orderId = 1; orderId <= 3; orderId++) {
            console.log(`📦 Order ${orderId}:`);
            const startTime = Date.now();

            try {
                await apiService.processOrderWithRetry(orderId);
                console.log(`   ✅ Processed (${Date.now() - startTime}ms)\n`);
            } catch (error) {
                console.log(
                    `   ❌ Failed after retries (${Date.now() - startTime}ms)\n`,
                );
            }
        }
    });

    // Reset stats
    apiService.resetStats();
    await sleep(2000);

    // Demo 3: Timeout
    await demoSection("Timeout Pattern", async () => {
        console.log("Test với timeout 2000ms...\n");

        for (let i = 1; i <= 5; i++) {
            const startTime = Date.now();

            try {
                await apiService.callApiWithTimeout(2000);
                console.log(
                    `  ✅ Request ${i}: Success (${Date.now() - startTime}ms)`,
                );
            } catch (error) {
                console.log(
                    `  ❌ Request ${i}: ${error.message} (${Date.now() - startTime}ms)`,
                );
            }
        }
    });

    // Reset stats
    apiService.resetStats();
    await sleep(2000);

    // Demo 4: Robust Request (kết hợp tất cả)
    await demoSection(
        "Robust Request (Circuit Breaker + Retry + Timeout)",
        async () => {
            console.log(
                "Gửi requests với đầy đủ fault tolerance mechanisms...\n",
            );

            for (let i = 1; i <= 5; i++) {
                const startTime = Date.now();

                try {
                    const result = await apiService.robustRequest(i);
                    console.log(
                        `  ✅ Request ${i}: Success (${Date.now() - startTime}ms)`,
                    );
                    if (result.fromFallback) {
                        console.log(`     └─ Using fallback data`);
                    }
                } catch (error) {
                    console.log(
                        `  ❌ Request ${i}: ${error.message} (${Date.now() - startTime}ms)`,
                    );
                }

                await sleep(800);
            }
        },
    );

    // Hiển thị thống kê tổng kết
    console.log("\n" + "╔" + "═".repeat(78) + "╗");
    console.log(
        "║" + " ".repeat(30) + "FINAL STATISTICS" + " ".repeat(32) + "║",
    );
    console.log("╚" + "═".repeat(78) + "╝\n");

    const stats = apiService.getServiceStats();
    console.log("📊 Service Statistics:");
    console.log(`   Total Requests: ${stats.totalRequests}`);
    console.log(`   Success: ${stats.successCount} (${stats.successRate})`);
    console.log(`   Failures: ${stats.failureCount}`);

    const circuitState = apiService.getCircuitBreakerState();
    console.log("\n🔌 Circuit Breaker State:");
    console.log(`   Current State: ${circuitState.state}`);
    console.log(`   Failure Count: ${circuitState.failureCount}`);
    console.log(`   Success Count: ${circuitState.successCount}`);

    console.log("\n✅ Demo completed!\n");
}

async function demoSection(title, fn) {
    console.log("\n" + "─".repeat(80));
    console.log(`🔹 ${title}`);
    console.log("─".repeat(80) + "\n");

    await fn();
}

function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

// Xử lý errors
process.on("unhandledRejection", (error) => {
    console.error("Unhandled error:", error);
    process.exit(1);
});

// Chạy demo
main().catch(console.error);
