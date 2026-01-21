package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:20 PM
 */

public class DeliveredState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("Đã giao: Cập nhật trạng thái đơn hàng là đã giao.");
        // trạng thái cuối -> không chuyển nữa
    }

    @Override
    public String name() {
        return "Đã giao";
    }
}
