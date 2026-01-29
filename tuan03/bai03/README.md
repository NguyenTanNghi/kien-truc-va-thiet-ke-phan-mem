# Bài 03: So sánh Technical Approach vs Domain Approach

## 📋 Mô tả

Dự án này minh họa 2 cách tổ chức code khác nhau cho cùng một bài toán (Login & Register):

1. **Technical Approach (Layered Architecture)** - Tổ chức theo layer kỹ thuật
2. **Domain Approach (Vertical Slice)** - Tổ chức theo domain/business

---

## 🏗️ 1. Technical Approach (Layered Architecture)

### Đặc điểm

- Tổ chức theo **các layer kỹ thuật**: Controllers → Services → Repositories → Models
- Tất cả controllers ở 1 folder, tất cả services ở 1 folder...
- Quen thuộc với kiến trúc MVC truyền thống

### Cấu trúc

```
01_technical_approach/
├── src/
│   ├── controllers/          ← Tất cả controllers
│   │   ├── authController.js
│   │   └── userController.js
│   ├── services/            ← Tất cả services
│   │   ├── authService.js
│   │   └── userService.js
│   ├── repositories/        ← Tất cả repositories
│   │   └── userRepository.js
│   ├── models/              ← Tất cả models
│   │   └── User.js
│   ├── middlewares/         ← Tất cả middlewares
│   └── routes/
```

### Ưu điểm ✅

- Dễ hiểu với developer mới
- Rõ ràng về technical responsibility
- Dễ tìm code theo layer

### Nhược điểm ❌

- Thêm feature mới phải sửa nhiều folder
- Logic bị phân tán
- Khó scale khi hệ thống lớn
- Coupling cao giữa các layer

---

## 🎯 2. Domain Approach (Vertical Slice)

### Đặc điểm

- Tổ chức theo **domain/business features**: auth, users, products...
- Mỗi domain chứa tất cả layer của nó (controller, service, repository...)
- Tập trung vào business use cases

### Cấu trúc

```
02_domain_approach/
├── src/
│   ├── domains/
│   │   ├── auth/                    ← Domain Authentication
│   │   │   ├── auth.controller.js
│   │   │   ├── auth.service.js
│   │   │   ├── auth.routes.js
│   │   │   └── index.js
│   │   │
│   │   └── users/                   ← Domain Users
│   │       ├── user.model.js
│   │       ├── user.repository.js
│   │       ├── user.service.js
│   │       ├── user.controller.js
│   │       ├── user.routes.js
│   │       └── index.js
│   │
│   └── shared/                      ← Code dùng chung
│       ├── middlewares/
│       └── utils/
```

### Ưu điểm ✅

- **High cohesion**: Code liên quan ở cùng chỗ
- **Low coupling**: Các domain độc lập
- Dễ scale: Thêm domain mới không ảnh hưởng domain cũ
- Dễ maintain: Sửa feature chỉ cần vào 1 domain
- Team có thể làm việc song song

### Nhược điểm ❌

- Xa lạ với developer quen MVC
- Có thể duplicate code
- Cần discipline để maintain boundaries

---

## 📊 So sánh chi tiết

| Tiêu chí            | Technical Approach                    | Domain Approach                      |
| ------------------- | ------------------------------------- | ------------------------------------ |
| **Tổ chức**         | Theo layer (controllers, services...) | Theo feature/domain (auth, users...) |
| **Cohesion**        | ❌ Low (logic phân tán)               | ✅ High (logic tập trung)            |
| **Coupling**        | ❌ High (layer phụ thuộc lẫn nhau)    | ✅ Low (domain độc lập)              |
| **Thêm feature**    | Sửa nhiều folder                      | Chỉ sửa 1 domain                     |
| **Scalability**     | ❌ Khó (nhiều files/folder)           | ✅ Dễ (thêm domain mới)              |
| **Maintainability** | ❌ Khó (qua nhiều folder)             | ✅ Dễ (trong 1 domain)               |
| **Learning curve**  | ✅ Dễ học (quen thuộc)                | ❌ Hơi khó (xa lạ)                   |
| **Team work**       | ❌ Conflict nhiều                     | ✅ Ít conflict                       |

---

## 🚀 Hướng dẫn chạy

### Technical Approach (Port 3000)

```bash
cd 01_technical_approach
npm install
npm start
```

### Domain Approach (Port 3001)

```bash
cd 02_domain_approach
npm install
npm start
```

---

## 🧪 Test API

### Technical Approach (http://localhost:3000)

**Register:**

```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","email":"john@example.com","password":"123456"}'
```

**Login:**

```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"123456"}'
```

**Get Profile:**

```bash
curl http://localhost:3000/api/users/profile \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### Domain Approach (http://localhost:3001)

**Register:**

```bash
curl -X POST http://localhost:3001/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"jane","email":"jane@example.com","password":"123456"}'
```

**Login:**

```bash
curl -X POST http://localhost:3001/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"jane@example.com","password":"123456"}'
```

**Get Profile:**

```bash
curl http://localhost:3001/api/users/profile \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 🎓 Khi nào dùng cách nào?

### Dùng Technical Approach khi:

- Team nhỏ, dự án đơn giản
- Developer quen với MVC truyền thống
- Hệ thống ít thay đổi

### Dùng Domain Approach khi:

- Dự án lớn, phức tạp
- Nhiều business domains
- Team lớn, làm việc song song
- Cần scale và maintain lâu dài
- Áp dụng Domain-Driven Design (DDD)

---

## 💡 Kết luận

**Technical Approach** phù hợp cho dự án nhỏ, đơn giản, team quen thuộc với MVC.

**Domain Approach** phù hợp cho dự án lớn, phức tạp, cần scale và maintain lâu dài.

Không có cách nào "đúng" tuyệt đối - tùy vào context của dự án! 🎯
