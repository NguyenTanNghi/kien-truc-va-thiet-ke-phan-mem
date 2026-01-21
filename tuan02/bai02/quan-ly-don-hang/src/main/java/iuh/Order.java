package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:19 PM
 */
public class Order {
    private OrderState state;

    public Order() {
        this.state = new NewState(); // mặc định: Mới tạo
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void process() {
        state.handle(this);
    }

    public String getStateName() {
        return state.name();
    }
}