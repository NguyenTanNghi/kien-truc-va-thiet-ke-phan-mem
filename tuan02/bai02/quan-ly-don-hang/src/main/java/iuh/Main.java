package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:17 PM
 */
public class Main {
    public static void main(String[] args) {
        Order order = new Order();

        System.out.println("State hiện tại: " + order.getStateName());
        order.process(); // New -> Processing

        System.out.println("State hiện tại: " + order.getStateName());
        order.process(); // Processing -> Delivered

        System.out.println("State hiện tại: " + order.getStateName());
        order.process(); // Delivered -> vẫn Delivered (kết thúc)

        // Demo hủy đơn (giả lập hủy thủ công)
        Order order2 = new Order();
        order2.setState(new CancelledState());
        System.out.println("\nOrder2 state: " + order2.getStateName());
        order2.process();
    }
}