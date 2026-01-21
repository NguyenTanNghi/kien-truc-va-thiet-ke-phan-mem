package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:24 PM
 */
public class VATTax implements TaxStrategy {
    public double calcTax(double price) { return price * 0.10; } // VAT 10%
    public String name() { return "VAT 10%"; }
}