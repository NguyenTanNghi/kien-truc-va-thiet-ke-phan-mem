# Technical Approach (Layered Architecture)

## Đặc điểm

Tổ chức code theo **các layer kỹ thuật** (technical layers):

- **Controllers**: Xử lý HTTP requests/responses
- **Services**: Business logic
- **Repositories**: Data access
- **Models**: Data structures
- **Middlewares**: Xử lý chung (auth, validation...)

## Cấu trúc thư mục

```
src/
├── controllers/          # Tất cả controllers
│   ├── authController.js
│   └── userController.js
├── services/            # Tất cả services
│   ├── authService.js
│   └── userService.js
├── repositories/        # Tất cả repositories
│   └── userRepository.js
├── models/              # Tất cả models
│   └── User.js
├── middlewares/         # Tất cả middlewares
│   ├── authMiddleware.js
│   └── errorHandler.js
├── routes/              # Định nghĩa routes
│   └── index.js
└── server.js            # Entry point
```

## Ưu điểm

✅ Dễ hiểu với developer mới (quen thuộc với MVC)
✅ Rõ ràng về technical responsibility
✅ Dễ tìm code theo layer (muốn sửa controller -> vào folder controllers)

## Nhược điểm

❌ Khi thêm feature mới phải sửa nhiều folder (controller, service, repository...)
❌ Khó theo dõi business logic (logic bị phân tán qua nhiều layer)
❌ Khó scale khi hệ thống lớn (quá nhiều files trong 1 folder)
❌ Coupling cao giữa các layer

## Chạy ứng dụng

```bash
npm install
npm start
```

## Test API

### Register

```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","email":"john@example.com","password":"123456"}'
```

### Login

```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"123456"}'
```

### Get Profile (cần token)

```bash
curl http://localhost:3000/api/users/profile \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```
