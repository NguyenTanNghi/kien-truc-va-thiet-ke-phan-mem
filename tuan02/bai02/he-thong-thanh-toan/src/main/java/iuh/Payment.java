package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:27 PM
 */
public interface Payment {
    double pay(double amount);
    String method();
}