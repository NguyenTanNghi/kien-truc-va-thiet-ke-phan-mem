/**
 * Error Handler Middleware
 * Xử lý tất cả errors trong application
 */

export const errorHandler = (err, req, res, next) => {
    console.error("Error:", err.message);
    console.error("Stack:", err.stack);

    res.status(err.status || 500).json({
        success: false,
        message: err.message || "Internal Server Error",
        ...(process.env.NODE_ENV === "development" && { stack: err.stack }),
    });
};
