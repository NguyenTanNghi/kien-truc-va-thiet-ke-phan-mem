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

### 6. Test hệ thống

#### Bước 6.1: Kiểm tra PM2 đang chạy

```powershell
# Xem trạng thái tất cả services
pm2 status

# Bạn sẽ thấy 4 processes:
# - users-service-0, users-service-1 (2 instances)
# - product-service-0, product-service-1 (2 instances)
# Tất cả phải có status: online
```

#### Bước 6.2: Test Users Service

**PowerShell:**

```powershell
# Test lần 1
Invoke-RestMethod -Uri http://localhost:9090/users

# Test lần 2 (để thấy PID thay đổi - Load Balancing)
Invoke-RestMethod -Uri http://localhost:9090/users

# Test lần 3
Invoke-RestMethod -Uri http://localhost:9090/users
```

**Kết quả mong đợi:**

```json
{
    "service": "users",
    "pid": 12345,
    "hostname": "YOUR-PC",
    "time": "2026-01-28T10:30:00.000Z"
}
```

💡 **LƯU Ý**: Mỗi lần gọi, bạn sẽ thấy **PID khác nhau** (ví dụ: 12345, 12346). Đó là bằng chứng **Load Balancing đang hoạt động** - request được phân phối đều giữa 2 instances!

#### Bước 6.3: Test Product Service

**PowerShell:**

```powershell
# Test product service
Invoke-RestMethod -Uri http://localhost:9090/product
Invoke-RestMethod -Uri http://localhost:9090/product
Invoke-RestMethod -Uri http://localhost:9090/product
```

**Kết quả mong đợi:**

```json
{
    "service": "product",
    "pid": 23456,
    "hostname": "YOUR-PC",
    "time": "2026-01-28T10:30:00.000Z"
}
```

💡 **LƯU Ý**: PID cũng sẽ thay đổi giữa các request!

#### Bước 6.4: Test nhiều lần liên tục (Load Testing)

**PowerShell:**

```powershell
# Test 10 lần liên tục - xem PID thay đổi
1..10 | ForEach-Object {
    Write-Host "Request $_" -ForegroundColor Cyan
    $result = Invoke-RestMethod -Uri http://localhost:9090/users
    Write-Host "  PID: $($result.pid) - Time: $($result.time)" -ForegroundColor Green
}
```

**Kết quả mẫu:**

```
Request 1
  PID: 12345 - Time: 2026-01-28T10:30:01.123Z
Request 2
  PID: 12346 - Time: 2026-01-28T10:30:01.456Z
Request 3
  PID: 12345 - Time: 2026-01-28T10:30:01.789Z
Request 4
  PID: 12346 - Time: 2026-01-28T10:30:02.012Z
...
```

✅ **Thành công**: Bạn thấy PID luân phiên giữa 2 giá trị → Load Balancing hoạt động!

---

## 🚀 Test High Availability (Độ sẵn sàng cao)

Bây giờ test phần quan trọng nhất: **Hệ thống có chạy tiếp khi 1 instance chết không?**

### Test 7.1: Kill 1 instance và kiểm tra hệ thống vẫn hoạt động

#### Bước 1: Xem danh sách processes đang chạy

```powershell
pm2 status
```

**Kết quả:**

```
┌─────┬──────────────────┬─────────────┬─────────┬─────────┬──────────┐
│ id  │ name             │ mode        │ status  │ cpu     │ memory   │
├─────┼──────────────────┼─────────────┼─────────┼─────────┼──────────┤
│ 0   │ users-service    │ cluster     │ online  │ 0%      │ 45.2mb   │
│ 1   │ users-service    │ cluster     │ online  │ 0%      │ 43.8mb   │
│ 2   │ product-service  │ cluster     │ online  │ 0%      │ 44.5mb   │
│ 3   │ product-service  │ cluster     │ online  │ 0%      │ 42.9mb   │
└─────┴──────────────────┴─────────────┴─────────┴─────────┴──────────┘
```

#### Bước 2: Kill 1 instance của users-service

```powershell
# Kill instance id=0 (users-service instance đầu tiên)
pm2 delete 0

# Hoặc stop nếu muốn giữ lại
# pm2 stop 0
```

#### Bước 3: Test ngay lập tức - Hệ thống VẪN hoạt động!

```powershell
# Test liên tục 5 lần
1..5 | ForEach-Object {
    Write-Host "Request $_" -ForegroundColor Yellow
    $result = Invoke-RestMethod -Uri http://localhost:9090/users
    Write-Host "  ✓ SUCCESS - PID: $($result.pid)" -ForegroundColor Green
}
```

**Kết quả:**

```
Request 1
  ✓ SUCCESS - PID: 12346
Request 2
  ✓ SUCCESS - PID: 12346
Request 3
  ✓ SUCCESS - PID: 12346
Request 4
  ✓ SUCCESS - PID: 12346
Request 5
  ✓ SUCCESS - PID: 12346
```

✅ **Thành công**: Hệ thống VẪN trả về kết quả! Instance còn lại (PID 12346) đang phục vụ tất cả request!

#### Bước 4: PM2 tự động restart instance bị kill

```powershell
# Chờ vài giây rồi kiểm tra lại
Start-Sleep -Seconds 5
pm2 status
```

**Kết quả:**

```
┌─────┬──────────────────┬─────────────┬─────────┬──────────┬──────────┐
│ id  │ name             │ mode        │ status  │ restarts │ memory   │
├─────┼──────────────────┼─────────────┼─────────┼──────────┼──────────┤
│ 0   │ users-service    │ cluster     │ online  │ 1        │ 42.1mb   │  ← Đã restart!
│ 1   │ users-service    │ cluster     │ online  │ 0        │ 43.8mb   │
│ 2   │ product-service  │ cluster     │ online  │ 0        │ 44.5mb   │
│ 3   │ product-service  │ cluster     │ online  │ 0        │ 42.9mb   │
└─────┴──────────────────┴─────────────┴─────────┴──────────┴──────────┘
```

✅ **PM2 Auto-restart**: Instance id=0 đã được restart tự động (xem cột "restarts" = 1)!

#### Bước 5: Test lại - Load Balancing hoạt động trở lại

```powershell
# Test 5 lần nữa
1..5 | ForEach-Object {
    $result = Invoke-RestMethod -Uri http://localhost:9090/users
    Write-Host "Request $_ - PID: $($result.pid)" -ForegroundColor Cyan
}
```

**Kết quả:**

```
Request 1 - PID: 12346
Request 2 - PID: 15789  ← Instance mới sau khi restart
Request 3 - PID: 12346
Request 4 - PID: 15789
Request 5 - PID: 12346
```

✅ **Hoàn hảo**: Cả 2 instances đều hoạt động, Load Balancing lại bình thường!

---

### Test 7.2: Simulate Crash (Giả lập process bị crash)

#### Bước 1: Xem logs để biết PID thực tế

```powershell
pm2 logs users-service --lines 20
```

Tìm dòng như: `[USERS-SERVICE] Server started on port 3001 with PID: 12345`

#### Bước 2: Kill process bằng Windows Task Manager

```powershell
# Giả sử PID là 12345
taskkill /PID 12345 /F
```

#### Bước 3: Test ngay - Vẫn hoạt động!

```powershell
Invoke-RestMethod -Uri http://localhost:9090/users
```

✅ **Kết quả**: Vẫn nhận được response từ instance còn lại!

#### Bước 4: Kiểm tra PM2 auto-restart

```powershell
pm2 status
pm2 logs users-service --lines 5
```

✅ **PM2 đã restart**: Instance bị kill đã được khởi động lại tự động!

---

## 📊 Tổng kết Test

### ✅ Các tính năng đã test thành công:

1. **Load Balancing**: ✓ Request được phân phối đều giữa 2 instances (PID thay đổi)
2. **High Availability**: ✓ Kill 1 instance, hệ thống vẫn hoạt động
3. **Auto Restart**: ✓ PM2 tự động restart instance bị kill trong vài giây
4. **Failover**: ✓ Instance còn lại tiếp tục phục vụ khi instance kia chết
5. **Zero Downtime**: ✓ Không có downtime trong quá trình restart

### 📈 Kiểm tra Performance

```powershell
# Xem CPU & Memory usage
pm2 monit

# Xem logs realtime
pm2 logs

# Xem thông tin chi tiết 1 service
pm2 show users-service
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
