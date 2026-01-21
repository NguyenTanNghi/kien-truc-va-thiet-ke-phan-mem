package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:19 PM
 */
public interface OrderState {
    void handle(Order order);
    String name();
}