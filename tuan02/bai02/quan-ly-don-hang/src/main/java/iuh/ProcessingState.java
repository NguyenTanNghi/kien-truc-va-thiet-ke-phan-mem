package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:19 PM
 */
public class ProcessingState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("Đang xử lý: Đóng gói và vận chuyển...");
        order.setState(new DeliveredState());
    }

    @Override
    public String name() {
        return "Đang xử lý";
    }
}