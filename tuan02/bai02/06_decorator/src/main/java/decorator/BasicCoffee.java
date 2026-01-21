package decorator;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:14 PM
 */
public class BasicCoffee implements Coffee {
    public String getDescription() { return "Basic Coffee"; }
    public int cost() { return 10; }
}