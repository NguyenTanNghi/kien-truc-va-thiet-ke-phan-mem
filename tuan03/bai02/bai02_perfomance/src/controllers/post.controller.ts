import { Request, Response, NextFunction } from 'express';
import { postService } from '../services/post.service';
import { calculatePagination } from '../utils/paginate';
import { GetPostsQuery } from '../middlewares/validate.middleware';

/**
 * Controller xử lý các request liên quan đến posts
 */
export class PostController {
    /**
     * GET /api/v1/posts
     * Lấy danh sách posts với phân trang
     */
    async getPosts(req: Request, res: Response, next: NextFunction): Promise<void> {
        try {
            // Query params đã được validate bởi middleware
            const { page, limit, sort, order, q } = req.query as unknown as GetPostsQuery;

            // Lấy tổng số items (sau khi filter nếu có)
            const totalItems = postService.getTotalCount(q);

            // Lấy data cho trang hiện tại
            const posts = postService.getPosts({
                page,
                limit,
                sort,
                order,
                q,
            });

            // Tính toán thông tin phân trang
            const pagination = calculatePagination(page, limit, totalItems);

            // Trả về response
            res.status(200).json({
                data: posts,
                pagination,
            });
        } catch (error) {
            next(error);
        }
    }
}

// Export singleton instance
export const postController = new PostController();
