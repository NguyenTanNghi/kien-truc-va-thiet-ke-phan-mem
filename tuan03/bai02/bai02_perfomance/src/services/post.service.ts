import * as fs from 'fs';
import * as path from 'path';

/**
 * Interface cho Post
 */
export interface Post {
    id: number;
    title: string;
    content: string;
    author: string;
    createdAt: string;
}

/**
 * Interface cho options tìm kiếm và sắp xếp
 */
export interface GetPostsOptions {
    page: number;
    limit: number;
    sort: 'createdAt' | 'title';
    order: 'asc' | 'desc';
    q?: string;
}

/**
 * Service xử lý business logic liên quan đến posts
 */
class PostService {
    private posts: Post[] | null = null;
    private readonly DATA_FILE_PATH: string;

    constructor() {
        // Đường dẫn tới file posts.json
        this.DATA_FILE_PATH = path.join(process.cwd(), 'src', 'data', 'posts.json');
    }

    /**
     * Đọc dữ liệu từ file JSON và cache trong memory
     */
    private loadPosts(): Post[] {
        // Nếu đã có cache, trả về ngay
        if (this.posts !== null) {
            return this.posts;
        }

        try {
            // Kiểm tra file có tồn tại không
            if (!fs.existsSync(this.DATA_FILE_PATH)) {
                throw new Error(
                    `Data file not found at ${this.DATA_FILE_PATH}. Please run "npm run gen:data" first.`
                );
            }

            // Đọc file và parse JSON
            const fileContent = fs.readFileSync(this.DATA_FILE_PATH, 'utf-8');
            this.posts = JSON.parse(fileContent) as Post[];

            console.log(`[PostService] Loaded ${this.posts.length} posts from JSON file`);

            return this.posts;
        } catch (error) {
            console.error('[PostService] Error loading posts:', error);
            throw error;
        }
    }

    /**
     * Lấy danh sách posts với phân trang, tìm kiếm và sắp xếp
     */
    getPosts(options: GetPostsOptions): Post[] {
        const { page, limit, sort, order, q } = options;

        // Load posts từ cache hoặc file
        let posts = this.loadPosts();

        // Tìm kiếm nếu có query
        if (q && q.trim()) {
            const searchTerm = q.toLowerCase().trim();
            posts = posts.filter(
                (post) =>
                    post.title.toLowerCase().includes(searchTerm) ||
                    post.content.toLowerCase().includes(searchTerm)
            );
        }

        // Sắp xếp
        posts = this.sortPosts(posts, sort, order);

        // Phân trang: lấy subset
        const offset = (page - 1) * limit;
        const paginatedPosts = posts.slice(offset, offset + limit);

        return paginatedPosts;
    }

    /**
     * Lấy tổng số posts (sau khi filter nếu có)
     */
    getTotalCount(q?: string): number {
        let posts = this.loadPosts();

        // Nếu có tìm kiếm, phải filter trước khi đếm
        if (q && q.trim()) {
            const searchTerm = q.toLowerCase().trim();
            posts = posts.filter(
                (post) =>
                    post.title.toLowerCase().includes(searchTerm) ||
                    post.content.toLowerCase().includes(searchTerm)
            );
        }

        return posts.length;
    }

    /**
     * Sắp xếp posts
     */
    private sortPosts(
        posts: Post[],
        sort: 'createdAt' | 'title',
        order: 'asc' | 'desc'
    ): Post[] {
        const sorted = [...posts].sort((a, b) => {
            let comparison = 0;

            if (sort === 'createdAt') {
                const dateA = new Date(a.createdAt).getTime();
                const dateB = new Date(b.createdAt).getTime();
                comparison = dateA - dateB;
            } else {
                // sort by title
                comparison = a.title.localeCompare(b.title);
            }

            return order === 'asc' ? comparison : -comparison;
        });

        return sorted;
    }
}

// Export singleton instance
export const postService = new PostService();
