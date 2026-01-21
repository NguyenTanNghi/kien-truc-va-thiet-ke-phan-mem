package decorator;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:14 PM
 */
public abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}