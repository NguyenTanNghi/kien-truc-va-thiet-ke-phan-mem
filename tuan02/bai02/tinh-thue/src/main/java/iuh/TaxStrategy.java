package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:24 PM
 */
public interface TaxStrategy {
    double calcTax(double price);
    String name();
}