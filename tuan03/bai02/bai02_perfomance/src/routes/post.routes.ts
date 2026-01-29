import { Router } from 'express';
import { postController } from '../controllers/post.controller';
import { validateGetPostsQuery } from '../middlewares/validate.middleware';

const router = Router();

/**
 * GET /api/v1/posts
 * Lấy danh sách posts với phân trang
 */
router.get('/', validateGetPostsQuery, (req, res, next) => {
    postController.getPosts(req, res, next);
});

export default router;
