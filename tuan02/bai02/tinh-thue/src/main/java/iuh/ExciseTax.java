package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:24 PM
 */
public class ExciseTax implements TaxStrategy {
    public double calcTax(double price) { return price * 0.20; } // tiêu thụ 20%
    public String name() { return "Excise 20%"; }
}

