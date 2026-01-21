package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:24 PM
 */
public class SpecialTax implements TaxStrategy {
    public double calcTax(double price) { return price * 0.30; } // đặc biệt 30% (xa xỉ)
    public String name() { return "Special 30%"; }
}