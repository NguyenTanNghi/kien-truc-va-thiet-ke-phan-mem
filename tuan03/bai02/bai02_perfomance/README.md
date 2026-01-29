# REST API Demo với Pagination

Ứng dụng Node.js + Express + TypeScript demo endpoint GET có phân trang, đọc dữ liệu từ file JSON.

## Công nghệ

- **Node.js** + **Express**
- **TypeScript**
- **Zod** (validation)
- **CORS**, **Morgan** (logging)
- Đọc dữ liệu từ file JSON với cơ chế cache trong memory

## Cấu trúc thư mục

```
rest-pagination-json-demo/
├─ package.json
├─ README.md
├─ tsconfig.json
└─ src/
   ├─ app.ts                    # Khởi tạo Express app
   ├─ server.ts                 # Entry point
   ├─ routes/
   │  └─ post.routes.ts         # Routes cho posts
   ├─ controllers/
   │  └─ post.controller.ts     # Controller xử lý request
   ├─ services/
   │  └─ post.service.ts        # Business logic + đọc JSON
   ├─ middlewares/
   │  ├─ error.middleware.ts    # Error handler
   │  └─ validate.middleware.ts # Validation middleware
   ├─ utils/
   │  └─ paginate.ts            # Utility phân trang
   └─ data/
      ├─ posts.json             # Dữ liệu demo (250 posts)
      └─ generate-posts.ts      # Script tạo dữ liệu
```

## Hướng dẫn chạy

### 1. Cài đặt dependencies

```bash
npm install
```

### 2. Tạo dữ liệu demo

```bash
npm run gen:data
```

Lệnh này sẽ tạo file `src/data/posts.json` với 250 bài posts.

### 3. Chạy development server

```bash
npm run dev
```

Server sẽ chạy tại `http://localhost:8080`

### 4. (Optional) Build và chạy production

```bash
npm run build
npm start
```

## API Endpoint

### GET `/api/v1/posts`

Lấy danh sách posts với phân trang.

**Query Parameters:**

| Param   | Type    | Default    | Mô tả                                  |
| ------- | ------- | ---------- | -------------------------------------- |
| `page`  | integer | 1          | Số trang (>= 1)                        |
| `limit` | integer | 10         | Số item mỗi trang (1-100)              |
| `sort`  | string  | createdAt  | Sắp xếp theo: `createdAt` hoặc `title` |
| `order` | string  | desc       | Thứ tự: `asc` hoặc `desc`              |
| `q`     | string  | (optional) | Tìm kiếm trong title/content           |

**Response thành công (200):**

```json
{
    "data": [
        {
            "id": 1,
            "title": "Post title 1",
            "content": "Content...",
            "author": "Author A",
            "createdAt": "2026-01-28T00:00:00.000Z"
        }
    ],
    "pagination": {
        "page": 1,
        "limit": 10,
        "totalItems": 250,
        "totalPages": 25,
        "hasNext": true,
        "hasPrev": false
    }
}
```

**Response lỗi (400):**

```json
{
  "message": "Invalid pagination params",
  "details": { ... }
}
```

## Ví dụ sử dụng

```bash
# Lấy trang 1, 10 items
curl "http://localhost:8080/api/v1/posts"

# Lấy trang 2, 20 items
curl "http://localhost:8080/api/v1/posts?page=2&limit=20"

# Sắp xếp theo title tăng dần
curl "http://localhost:8080/api/v1/posts?sort=title&order=asc"

# Tìm kiếm
curl "http://localhost:8080/api/v1/posts?q=node"

# Kết hợp
curl "http://localhost:8080/api/v1/posts?page=2&limit=20&sort=createdAt&order=desc&q=node"
```

## Tối ưu hiệu năng

- **Cache trong memory**: File JSON được đọc 1 lần duy nhất khi khởi động, sau đó cache lại để tránh I/O.
- **Pagination**: Chỉ trả về số lượng items cần thiết, không load toàn bộ danh sách.
- **Validation**: Validate input ngay từ đầu để tránh xử lý không cần thiết.

## License

MIT
