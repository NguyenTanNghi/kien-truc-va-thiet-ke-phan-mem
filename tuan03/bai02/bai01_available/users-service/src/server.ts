import express, { Request, Response } from 'express';
import os from 'os';

const app = express();
const PORT = process.env.PORT || 3001;

// Middleware
app.use(express.json());

// GET /users endpoint
app.get('/users', (req: Request, res: Response) => {
    const response = {
        service: 'users',
        pid: process.pid,
        hostname: os.hostname(),
        time: new Date().toISOString()
    };

    console.log(`[USERS-SERVICE] Request handled by PID: ${process.pid}`);
    res.json(response);
});

// Health check endpoint
app.get('/health', (req: Request, res: Response) => {
    res.send('OK');
});

// Start server
app.listen(PORT, () => {
    console.log(`[USERS-SERVICE] Server started on port ${PORT} with PID: ${process.pid}`);
    console.log(`[USERS-SERVICE] Hostname: ${os.hostname()}`);
});

// Graceful shutdown
process.on('SIGINT', () => {
    console.log(`[USERS-SERVICE] PID ${process.pid} shutting down gracefully...`);
    process.exit(0);
});

process.on('SIGTERM', () => {
    console.log(`[USERS-SERVICE] PID ${process.pid} shutting down gracefully...`);
    process.exit(0);
});
