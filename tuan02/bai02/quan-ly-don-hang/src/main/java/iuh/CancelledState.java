package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:20 PM
 */
public class CancelledState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("Hủy: Hủy đơn hàng và hoàn tiền.");
        // trạng thái cuối
    }

    @Override
    public String name() {
        return "Hủy";
    }
}