package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:27 PM
 */
public class CreditCardPayment implements Payment {
    @Override
    public double pay(double amount) {
        System.out.println("Pay by Credit Card: " + amount);
        return amount;
    }

    @Override
    public String method() {
        return "CreditCard";
    }
}
