# Domain Approach (Vertical Slice Architecture)

## Đặc điểm

Tổ chức code theo **domain/business logic** (business features):

- Mỗi domain/module là một đơn vị độc lập
- Chứa tất cả layers cần thiết trong 1 folder
- Tập trung vào business use cases

## Cấu trúc thư mục

```
src/
├── domains/
│   ├── auth/                    # Domain Authentication
│   │   ├── auth.controller.js   # Controller của auth
│   │   ├── auth.service.js      # Service của auth
│   │   ├── auth.routes.js       # Routes của auth
│   │   └── index.js             # Public API của auth domain
│   │
│   └── users/                   # Domain Users
│       ├── user.model.js        # User model
│       ├── user.repository.js   # User repository
│       ├── user.service.js      # User service
│       ├── user.controller.js   # User controller
│       ├── user.routes.js       # User routes
│       └── index.js             # Public API của users domain
│
├── shared/                      # Code dùng chung
│   ├── middlewares/
│   │   ├── authMiddleware.js
│   │   └── errorHandler.js
│   └── utils/
│       └── jwtUtils.js
│
└── server.js                    # Entry point
```

## Ưu điểm

✅ High cohesion: Code liên quan đến 1 feature ở cùng chỗ
✅ Low coupling: Các domain độc lập với nhau
✅ Dễ scale: Thêm domain mới không ảnh hưởng domain cũ
✅ Dễ maintain: Sửa feature chỉ cần vào 1 folder domain
✅ Dễ test: Test toàn bộ feature trong 1 domain
✅ Team có thể làm việc song song trên các domain khác nhau

## Nhược điểm

❌ Xa lạ với developer quen MVC truyền thống
❌ Có thể duplicate code giữa các domain
❌ Cần discipline để maintain boundaries

## Chạy ứng dụng

```bash
npm install
npm start
```

## Test API

### Register

```bash
curl -X POST http://localhost:3001/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"jane","email":"jane@example.com","password":"123456"}'
```

### Login

```bash
curl -X POST http://localhost:3001/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"jane@example.com","password":"123456"}'
```

### Get Profile (cần token)

```bash
curl http://localhost:3001/api/users/profile \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## So sánh với Technical Approach

| Aspect              | Technical Approach                        | Domain Approach                       |
| ------------------- | ----------------------------------------- | ------------------------------------- |
| **Tổ chức**         | Theo layer (controllers, services...)     | Theo feature/domain (auth, users...)  |
| **Cohesion**        | Low (logic phân tán qua nhiều folder)     | High (logic tập trung trong 1 domain) |
| **Coupling**        | High (layer phụ thuộc lẫn nhau)           | Low (domain độc lập)                  |
| **Scalability**     | Khó (nhiều files trong 1 folder)          | Dễ (thêm domain mới)                  |
| **Maintainability** | Khó (sửa 1 feature phải qua nhiều folder) | Dễ (chỉ sửa trong domain)             |
