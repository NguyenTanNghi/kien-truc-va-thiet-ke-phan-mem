import express, { Request, Response } from 'express';
import os from 'os';

const app = express();
const PORT = process.env.PORT || 3002;

// Middleware
app.use(express.json());

// GET /product endpoint
app.get('/product', (req: Request, res: Response) => {
    const response = {
        service: 'product',
        pid: process.pid,
        hostname: os.hostname(),
        time: new Date().toISOString()
    };

    console.log(`[PRODUCT-SERVICE] Request handled by PID: ${process.pid}`);
    res.json(response);
});

// Health check endpoint
app.get('/health', (req: Request, res: Response) => {
    res.send('OK');
});

// Start server
app.listen(PORT, () => {
    console.log(`[PRODUCT-SERVICE] Server started on port ${PORT} with PID: ${process.pid}`);
    console.log(`[PRODUCT-SERVICE] Hostname: ${os.hostname()}`);
});

// Graceful shutdown
process.on('SIGINT', () => {
    console.log(`[PRODUCT-SERVICE] PID ${process.pid} shutting down gracefully...`);
    process.exit(0);
});

process.on('SIGTERM', () => {
    console.log(`[PRODUCT-SERVICE] PID ${process.pid} shutting down gracefully...`);
    process.exit(0);
});
