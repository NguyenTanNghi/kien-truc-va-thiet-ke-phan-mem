package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:27 PM
 */
public class PaypalPayment implements Payment {
    @Override
    public double pay(double amount) {
        System.out.println("Pay by PayPal: " + amount);
        return amount;
    }

    @Override
    public String method() {
        return "PayPal";
    }
}