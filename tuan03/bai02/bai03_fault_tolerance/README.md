# Bài 03: Fault Tolerance (Khả năng chịu lỗi)

## Mô tả

Dự án này minh họa các kỹ thuật xử lý lỗi và đảm bảo tính ổn định của hệ thống khi gặp các vấn đề:

- **Lỗi mạng** (network errors)
- **Server chập chờn** (server crashes)
- **Timeout**
- **Lưu lượng truy cập lớn** (high traffic)

## Các Pattern được sử dụng

### 1. Circuit Breaker Pattern

- Ngăn chặn cascade failure khi service bị lỗi
- Tự động "mở mạch" khi phát hiện nhiều lỗi liên tiếp
- Tự động thử lại sau một khoảng thời gian (half-open state)

### 2. Retry Pattern

- Tự động thử lại khi gặp lỗi tạm thời
- Exponential backoff: tăng thời gian chờ giữa các lần retry
- Giới hạn số lần retry để tránh retry vô hạn

### 3. Timeout Pattern

- Đặt giới hạn thời gian cho mỗi request
- Tránh treo hệ thống khi service phản hồi chậm

## Cài đặt

```bash
npm install
```

## Chạy demo

### Demo Circuit Breaker

```bash
npm run demo-circuit-breaker
```

### Demo Retry Pattern

```bash
npm run demo-retry
```

### Demo Timeout

```bash
npm run demo-timeout
```

### Chạy tất cả

```bash
npm start
```

## Cấu trúc thư mục

```
src/
├── index.js                    # Entry point chính
├── circuit-breaker-demo.js     # Demo Circuit Breaker
├── retry-demo.js               # Demo Retry Pattern
├── timeout-demo.js             # Demo Timeout
├── patterns/
│   ├── CircuitBreaker.js       # Implementation Circuit Breaker
│   ├── RetryHandler.js         # Implementation Retry với backoff
│   └── TimeoutHandler.js       # Implementation Timeout
└── services/
    ├── UnstableService.js      # Mock service không ổn định
    └── ApiService.js           # Service với fault tolerance
```

## Kết quả mong đợi

- Circuit Breaker sẽ tự động "mở mạch" sau nhiều lỗi liên tiếp
- Retry sẽ thử lại với thời gian chờ tăng dần
- Timeout sẽ hủy request nếu mất quá nhiều thời gian
- Hệ thống vẫn hoạt động được dù có lỗi xảy ra
