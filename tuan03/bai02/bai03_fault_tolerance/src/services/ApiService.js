/**
 * API Service với Fault Tolerance
 *
 * Kết hợp Circuit Breaker, Retry và Timeout patterns
 */

import { CircuitBreaker } from "../patterns/CircuitBreaker.js";
import { RetryHandler } from "../patterns/RetryHandler.js";
import { TimeoutHandler } from "../patterns/TimeoutHandler.js";
import { UnstableService } from "./UnstableService.js";

export class ApiService {
    constructor() {
        // Khởi tạo unstable service để test
        this.unstableService = new UnstableService({
            failureRate: 0.4, // 40% fail rate
            minDelay: 100,
            maxDelay: 2000,
        });

        // Khởi tạo fault tolerance patterns
        this.circuitBreaker = new CircuitBreaker({
            failureThreshold: 3,
            resetTimeout: 10000, // 10 giây
            halfOpenRequests: 2,
        });

        this.retryHandler = new RetryHandler({
            maxRetries: 3,
            initialDelay: 1000,
            backoffMultiplier: 2,
        });

        this.timeoutHandler = new TimeoutHandler({
            defaultTimeout: 5000,
        });
    }

    // Get data với Circuit Breaker
    async getDataWithCircuitBreaker(id) {
        console.log(`\n🔵 Request: Get data ${id}`);

        return this.circuitBreaker.execute(
            async () => {
                return await this.unstableService.getData(id);
            },
            // Fallback function nếu circuit open
            () => {
                console.log("⚠️  Using cached/fallback data");
                return {
                    id,
                    data: "Cached data (fallback)",
                    timestamp: new Date().toISOString(),
                    fromCache: true,
                };
            },
        );
    }

    // Process order với Retry
    async processOrderWithRetry(orderId) {
        console.log(`\n🔵 Request: Process order ${orderId}`);

        return this.retryHandler.execute(async () => {
            return await this.unstableService.processOrder(orderId);
        }, ["ECONNREFUSED", "ETIMEDOUT", "Service unavailable"]);
    }

    // Call API với Timeout
    async callApiWithTimeout(timeout = 3000) {
        console.log(`\n🔵 Request: Call external API (timeout: ${timeout}ms)`);

        return this.timeoutHandler.execute(async () => {
            return await this.unstableService.callExternalAPI();
        }, timeout);
    }

    // Kết hợp tất cả patterns
    async robustRequest(id) {
        console.log(`\n🔵 Robust Request: Get data ${id}`);
        console.log("Using: Circuit Breaker + Retry + Timeout");

        return this.circuitBreaker.execute(
            async () => {
                return this.retryHandler.execute(async () => {
                    return this.timeoutHandler.execute(
                        async () => {
                            return await this.unstableService.getData(id);
                        },
                        3000, // 3 second timeout
                    );
                });
            },
            // Fallback
            () => {
                console.log(
                    "⚠️  All fault tolerance mechanisms failed, using fallback",
                );
                return {
                    id,
                    data: "Fallback data",
                    timestamp: new Date().toISOString(),
                    fromFallback: true,
                };
            },
        );
    }

    getCircuitBreakerState() {
        return this.circuitBreaker.getState();
    }

    getServiceStats() {
        return this.unstableService.getStats();
    }

    resetStats() {
        this.unstableService.reset();
    }
}
