import * as fs from 'fs';
import * as path from 'path';

/**
 * Interface cho Post
 */
interface Post {
    id: number;
    title: string;
    content: string;
    author: string;
    createdAt: string;
}

/**
 * Danh sách các author để random
 */
const AUTHORS = [
    'Author A',
    'Author B',
    'Author C',
    'John Doe',
    'Jane Smith',
    'Alice Johnson',
    'Bob Wilson',
    'Charlie Brown',
    'Diana Prince',
    'Eve Adams',
];

/**
 * Danh sách các từ khóa để tạo content ngẫu nhiên
 */
const KEYWORDS = [
    'Node.js', 'Express', 'TypeScript', 'REST API', 'pagination',
    'performance', 'optimization', 'database', 'JSON', 'backend',
    'frontend', 'fullstack', 'microservices', 'cloud', 'Docker',
    'Kubernetes', 'MongoDB', 'PostgreSQL', 'Redis', 'GraphQL',
];

/**
 * Random một phần tử từ array
 */
function randomItem<T>(arr: T[]): T {
    return arr[Math.floor(Math.random() * arr.length)];
}

/**
 * Tạo nội dung ngẫu nhiên
 */
function generateContent(): string {
    const sentences: string[] = [];
    const numSentences = Math.floor(Math.random() * 5) + 3; // 3-7 câu

    for (let i = 0; i < numSentences; i++) {
        const keyword = randomItem(KEYWORDS);
        const templates = [
            `This is an article about ${keyword}.`,
            `Learn more about ${keyword} in this comprehensive guide.`,
            `${keyword} is essential for modern web development.`,
            `Discover the best practices for ${keyword}.`,
            `How to effectively use ${keyword} in your projects.`,
        ];
        sentences.push(randomItem(templates));
    }

    return sentences.join(' ');
}

/**
 * Tạo một post
 */
function generatePost(id: number, baseDate: Date): Post {
    // Mỗi post cách nhau random từ 1-24 giờ
    const hoursOffset = Math.floor(Math.random() * 24) + 1;
    const createdAt = new Date(baseDate.getTime() - hoursOffset * 60 * 60 * 1000);

    return {
        id,
        title: `Post title ${id}: ${randomItem(KEYWORDS)}`,
        content: generateContent(),
        author: randomItem(AUTHORS),
        createdAt: createdAt.toISOString(),
    };
}

/**
 * Tạo danh sách posts
 */
function generatePosts(count: number): Post[] {
    const posts: Post[] = [];
    const baseDate = new Date(); // Ngày hiện tại

    for (let i = 1; i <= count; i++) {
        posts.push(generatePost(i, baseDate));
    }

    // Sắp xếp theo createdAt giảm dần (mới nhất trước)
    posts.sort((a, b) => {
        return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
    });

    return posts;
}

/**
 * Main function
 */
function main() {
    const NUM_POSTS = 250;
    const OUTPUT_FILE = path.join(__dirname, 'posts.json');

    console.log(`Generating ${NUM_POSTS} posts...`);

    const posts = generatePosts(NUM_POSTS);

    // Ghi ra file
    fs.writeFileSync(OUTPUT_FILE, JSON.stringify(posts, null, 2), 'utf-8');

    console.log(`✅ Successfully generated ${posts.length} posts`);
    console.log(`📁 File saved to: ${OUTPUT_FILE}`);
}

// Chạy script
main();
