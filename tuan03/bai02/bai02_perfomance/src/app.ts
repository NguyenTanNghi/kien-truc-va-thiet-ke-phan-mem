import express, { Application } from 'express';
import cors from 'cors';
import morgan from 'morgan';
import postRoutes from './routes/post.routes';
import { errorHandler, notFoundHandler } from './middlewares/error.middleware';

/**
 * Khởi tạo Express application
 */
export function createApp(): Application {
    const app = express();

    // Middlewares
    app.use(cors());
    app.use(morgan('dev'));
    app.use(express.json());
    app.use(express.urlencoded({ extended: true }));

    // Health check endpoint
    app.get('/health', (req, res) => {
        res.status(200).json({ status: 'OK', timestamp: new Date().toISOString() });
    });

    // API Routes
    app.use('/api/v1/posts', postRoutes);

    // 404 Handler
    app.use(notFoundHandler);

    // Error Handler
    app.use(errorHandler);

    return app;
}
