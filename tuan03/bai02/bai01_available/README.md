# Node.js High Availability + Load Balancing (TypeScript)

Project Node.js với TypeScript đảm bảo High Availability và Load Balancing sử dụng PM2 và Nginx (không Docker).

## Kiến trúc

```
Client -> Nginx (port 9090)
         |-- /users   -> users-service (PM2 cluster, 2 instances, port 3001)
         |-- /product -> product-service (PM2 cluster, 2 instances, port 3002)
```

## Công nghệ

- **Node.js + TypeScript + Express**: Backend services
- **PM2**: Process manager với cluster mode, auto-restart
- **Nginx**: Load balancer và reverse proxy
- **No Docker**: Chạy trực tiếp trên host

## Cấu trúc thư mục

```
node-available-lb-ts/
├─ nginx/
│  └─ nginx.conf
├─ users-service/
│  ├─ package.json
│  ├─ tsconfig.json
│  └─ src/
│     └─ server.ts
├─ product-service/
│  ├─ package.json
│  ├─ tsconfig.json
│  └─ src/
│     └─ server.ts
├─ ecosystem.config.cjs
└─ README.md
```

## Tính năng High Availability

1. **PM2 Cluster Mode**: Mỗi service chạy 2 instances
2. **Auto Restart**: PM2 tự động restart khi process crash
3. **Load Balancing**: Nginx phân phối request theo chiến lược least_conn
4. **Failover**: Khi 1 instance chết, instance khác vẫn phục vụ

## Cài đặt & Chạy

### 1. Cài đặt PM2 globally

```bash
npm install -g pm2
```

### 2. Cài đặt dependencies cho từng service

```bash
# Users service
cd users-service
npm install

# Product service
cd ../product-service
npm install
```

### 3. Build TypeScript thành JavaScript

```bash
# Users service
cd users-service
npm run build

# Product service
cd ../product-service
npm run build

# Quay lại root
cd ..
```

### 4. Khởi động services với PM2

```bash
# Từ thư mục root (node-available-lb-ts/)
pm2 start ecosystem.config.cjs

# Kiểm tra trạng thái
pm2 status

# Xem logs
pm2 logs
```

### 5. Khởi động Nginx

**Windows:**

```bash
# Lấy đường dẫn tuyệt đối
$nginxConfig = Resolve-Path .\nginx\nginx.conf

# Khởi động Nginx (cần cài đặt Nginx trước)
nginx -c $nginxConfig
```

**Linux/Mac:**

```bash
# Lấy đường dẫn tuyệt đối
NGINX_CONFIG=$(pwd)/nginx/nginx.conf

# Khởi động Nginx
nginx -c $NGINX_CONFIG
```

### 6. Test hệ thống

```bash
# Test users service
curl http://localhost:9090/users

# Test product service
curl http://localhost:9090/product

# Test nhiều lần để thấy PID thay đổi (load balancing)
for i in {1..10}; do curl http://localhost:9090/users; done
```

**Kết quả mẫu:**

```json
{
    "service": "users",
    "pid": 12345,
    "hostname": "YOUR-PC",
    "time": "2026-01-28T10:30:00.000Z"
}
```

## Test High Availability

### Test 1: Kill một process

```bash
# Xem danh sách processes
pm2 status

# Kill một instance (thay <id> bằng ID thực tế)
pm2 delete <id>

# Test lại - vẫn hoạt động với instance còn lại
curl http://localhost:9090/users

# PM2 sẽ tự restart instance bị kill
pm2 status
```

### Test 2: Restart toàn bộ

```bash
# Restart tất cả services
pm2 restart all

# Hệ thống vẫn available trong quá trình restart
curl http://localhost:9090/users
```

## Quản lý PM2

```bash
# Xem status
pm2 status

# Xem logs
pm2 logs
pm2 logs users-service
pm2 logs product-service

# Xem monitor
pm2 monit

# Restart
pm2 restart users-service
pm2 restart product-service
pm2 restart all

# Stop
pm2 stop all

# Delete
pm2 delete all

# Save configuration
pm2 save

# Startup script (auto-start on boot)
pm2 startup
```

## Quản lý Nginx

**Reload configuration:**

```bash
nginx -s reload
```

**Stop Nginx:**

```bash
nginx -s stop
```

**Quit gracefully:**

```bash
nginx -s quit
```

## Development Mode

Để development với hot reload:

```bash
# Users service
cd users-service
npm run dev

# Product service (terminal khác)
cd product-service
npm run dev
```

## Các endpoints

### Users Service (qua Nginx: 9090)

- `GET http://localhost:9090/users` - Lấy thông tin users service
- `GET http://localhost:3001/health` - Health check (direct)

### Product Service (qua Nginx: 9090)

- `GET http://localhost:9090/product` - Lấy thông tin product service
- `GET http://localhost:3002/health` - Health check (direct)

### Nginx

- `GET http://localhost:9090/health` - Nginx health check

## Troubleshooting

### Port đã được sử dụng

```bash
# Windows: Kill process trên port
netstat -ano | findstr :3001
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:3001 | xargs kill -9
```

### PM2 không start

```bash
# Xóa PM2 cache
pm2 kill
pm2 start ecosystem.config.cjs
```

### Nginx không start

- Kiểm tra file config: `nginx -t -c <path-to-nginx.conf>`
- Đảm bảo port 9090 chưa được sử dụng
- Kiểm tra logs của Nginx

## Cấu hình nâng cao

### Tăng số instances

Sửa file `ecosystem.config.cjs`:

```javascript
instances: 4,  // Tăng từ 2 lên 4
```

### Thêm monitoring

```bash
# PM2 Plus (cloud monitoring)
pm2 link <secret_key> <public_key>
```

### Load testing

```bash
# Cài Apache Bench
apt-get install apache2-utils  # Linux
brew install httpd             # Mac

# Test
ab -n 1000 -c 10 http://localhost:9090/users
```

## License

MIT
