const http = require("http");
const httpProxy = require("http-proxy");

const PORT = 9090;

// Tạo proxy server
const proxy = httpProxy.createProxyServer({});

// Danh sách upstream cho users-service (round-robin)
const usersUpstreams = ["http://localhost:3001"];
let usersIndex = 0;

// Danh sách upstream cho product-service (round-robin)
const productUpstreams = ["http://localhost:3002"];
let productIndex = 0;

// Xử lý lỗi proxy
proxy.on("error", (err, req, res) => {
    console.error(`[PROXY ERROR] ${req.url}:`, err.message);
    res.writeHead(502, { "Content-Type": "text/plain" });
    res.end("Bad Gateway - Service unavailable");
});

// Proxy success event
proxy.on("proxyRes", (proxyRes, req, res) => {
    console.log(
        `[${new Date().toISOString()}] ${req.method} ${req.url} -> ${proxyRes.statusCode}`,
    );
});

// Tạo HTTP server
const server = http.createServer((req, res) => {
    const { url, method } = req;

    console.log(`[REQUEST] ${method} ${url}`);

    // Route: /users -> users-service
    if (url.startsWith("/users")) {
        const target = usersUpstreams[usersIndex % usersUpstreams.length];
        usersIndex++;
        console.log(`  -> Forwarding to users-service: ${target}`);
        proxy.web(req, res, { target });
    }
    // Route: /product -> product-service
    else if (url.startsWith("/product")) {
        const target = productUpstreams[productIndex % productUpstreams.length];
        productIndex++;
        console.log(`  -> Forwarding to product-service: ${target}`);
        proxy.web(req, res, { target });
    }
    // Health check
    else if (url === "/health") {
        res.writeHead(200, { "Content-Type": "text/plain" });
        res.end("Load Balancer is running");
    }
    // Not found
    else {
        res.writeHead(404, { "Content-Type": "text/plain" });
        res.end("404 Not Found");
    }
});

// Start server
server.listen(PORT, () => {
    console.log("========================================");
    console.log("  NODE.JS LOAD BALANCER STARTED");
    console.log("========================================");
    console.log(`✓ Listening on: http://localhost:${PORT}`);
    console.log(`✓ Routes:`);
    console.log(`  - /users   -> http://localhost:3001 (users-service)`);
    console.log(`  - /product -> http://localhost:3002 (product-service)`);
    console.log(`  - /health  -> Load balancer health check`);
    console.log("========================================\n");
});

// Graceful shutdown
process.on("SIGINT", () => {
    console.log("\n[LOAD BALANCER] Shutting down gracefully...");
    server.close(() => {
        console.log("[LOAD BALANCER] Server closed");
        process.exit(0);
    });
});

process.on("SIGTERM", () => {
    console.log("\n[LOAD BALANCER] Shutting down gracefully...");
    server.close(() => {
        console.log("[LOAD BALANCER] Server closed");
        process.exit(0);
    });
});
