Vì sao chọn Decorator?

Bài toán có “phương thức thanh toán cơ bản” (CreditCard, PayPal, …) và có thể gắn thêm tính năng như:

Phí xử lý (cộng thêm tiền)

Mã giảm giá (trừ tiền)

Các tính năng này có thể kết hợp linh hoạt (vừa phí xử lý vừa giảm giá), và có thể thêm mới trong tương lai.
Decorator cho phép “bọc” (wrap) đối tượng thanh toán để mở rộng hành vi mà không sửa code lớp gốc, tránh tạo rất nhiều class kiểu PaypalWithFeeAndDiscount, CardWithFee, ...