package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:27 PM
 */
public class Main {
    public static void main(String[] args) {
        double amount = 100;

        // Thanh toán PayPal + phí xử lý + mã giảm giá
        Payment payment = new PaypalPayment();
        payment = new ProcessingFeeDecorator(payment, 5);   // +5 phí
        payment = new DiscountCodeDecorator(payment, 10);   // -10 giảm

        System.out.println("Method: " + payment.method());
        double finalPaid = payment.pay(amount);
        System.out.println("Final paid: " + finalPaid);

        System.out.println("------------------");

        // Thanh toán thẻ tín dụng + chỉ phí xử lý
        Payment cardPay = new CreditCardPayment();
        cardPay = new ProcessingFeeDecorator(cardPay, 3);

        System.out.println("Method: " + cardPay.method());
        System.out.println("Final paid: " + cardPay.pay(amount));
    }
}
