import { Request, Response, NextFunction } from 'express';
import { z, ZodError } from 'zod';

/**
 * Schema validation cho query params của GET /api/v1/posts
 */
export const getPostsQuerySchema = z.object({
    page: z
        .string()
        .optional()
        .default('1')
        .transform((val) => parseInt(val, 10))
        .refine((val) => !isNaN(val) && val >= 1, {
            message: 'page must be an integer >= 1',
        }),

    limit: z
        .string()
        .optional()
        .default('10')
        .transform((val) => parseInt(val, 10))
        .refine((val) => !isNaN(val) && val >= 1 && val <= 100, {
            message: 'limit must be an integer between 1 and 100',
        }),

    sort: z
        .enum(['createdAt', 'title'])
        .optional()
        .default('createdAt'),

    order: z
        .enum(['asc', 'desc'])
        .optional()
        .default('desc'),

    q: z
        .string()
        .optional(),
});

export type GetPostsQuery = z.infer<typeof getPostsQuerySchema>;

/**
 * Middleware validate query params
 */
export function validateGetPostsQuery(
    req: Request,
    res: Response,
    next: NextFunction
): void {
    try {
        const validated = getPostsQuerySchema.parse(req.query);

        // Gán lại query đã validated vào req
        req.query = validated as any;

        next();
    } catch (error) {
        if (error instanceof ZodError) {
            res.status(400).json({
                message: 'Invalid pagination params',
                details: error.errors.map((err) => ({
                    path: err.path.join('.'),
                    message: err.message,
                })),
            });
            return;
        }

        next(error);
    }
}
