import { Request, Response, NextFunction } from 'express';

/**
 * Error middleware xử lý tất cả lỗi trong app
 */
export function errorHandler(
    err: Error,
    req: Request,
    res: Response,
    next: NextFunction
): void {
    console.error('[Error]:', err);

    // Trả về JSON error
    res.status(500).json({
        message: err.message || 'Internal Server Error',
        details: process.env.NODE_ENV === 'development' ? err.stack : undefined,
    });
}

/**
 * Middleware xử lý route không tồn tại
 */
export function notFoundHandler(
    req: Request,
    res: Response,
    next: NextFunction
): void {
    res.status(404).json({
        message: `Route ${req.method} ${req.path} not found`,
    });
}
