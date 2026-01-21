package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:23 PM
 */
public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("T-Shirt", 100, new VATTax());
        p1.printBill();

        Product p2 = new Product("Alcohol", 200, new ExciseTax());
        p2.printBill();

        Product p3 = new Product("Luxury Watch", 1000, new SpecialTax());
        p3.printBill();

        // đổi thuế linh hoạt (ví dụ: hàng khuyến mãi chỉ tính VAT)
        p3.setTaxStrategy(new VATTax());
        p3.printBill();
    }
}