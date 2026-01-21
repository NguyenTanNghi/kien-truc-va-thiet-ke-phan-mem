package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:19 PM
 */
public class NewState implements OrderState {
    @Override
    public void handle(Order order) {
        System.out.println("Mới tạo: Kiểm tra thông tin đơn hàng...");
        order.setState(new ProcessingState());
    }

    @Override
    public String name() {
        return "Mới tạo";
    }
}