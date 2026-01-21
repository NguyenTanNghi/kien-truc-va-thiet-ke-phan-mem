Vì sao chọn State Pattern?

Bài toán của bạn là “đơn hàng có nhiều trạng thái (Mới tạo, Đang xử lý, Đã giao, Hủy) và mỗi trạng thái có hành vi khác nhau”.
State Pattern sinh ra để giải quyết đúng kiểu này: thay đổi hành vi của đối tượng khi trạng thái thay đổi, tránh if/else dài và dễ mở rộng (thêm trạng thái mới chỉ cần thêm class mới).