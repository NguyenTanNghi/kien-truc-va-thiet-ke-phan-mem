package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:28 PM
 */
public class ProcessingFeeDecorator extends PaymentDecorator {
    private final double fee; // phí cố định

    public ProcessingFeeDecorator(Payment payment, double fee) {
        super(payment);
        this.fee = fee;
    }

    @Override
    public double pay(double amount) {
        double newAmount = amount + fee;
        System.out.println("Add processing fee: +" + fee + " => " + newAmount);
        return payment.pay(newAmount);
    }
}