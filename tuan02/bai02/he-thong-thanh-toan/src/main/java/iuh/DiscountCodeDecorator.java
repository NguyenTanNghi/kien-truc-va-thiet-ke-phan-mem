package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:28 PM
 */
public class DiscountCodeDecorator extends PaymentDecorator {
    private final double discount; // giảm giá cố định

    public DiscountCodeDecorator(Payment payment, double discount) {
        super(payment);
        this.discount = discount;
    }

    @Override
    public double pay(double amount) {
        double newAmount = Math.max(0, amount - discount);
        System.out.println("Apply discount: -" + discount + " => " + newAmount);
        return payment.pay(newAmount);
    }
}