package decorator;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:14 PM
 */
public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) { super(coffee); }

    public String getDescription() { return coffee.getDescription() + " + Sugar"; }
    public int cost() { return coffee.cost() + 2; }
}