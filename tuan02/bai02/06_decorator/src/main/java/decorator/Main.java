package decorator;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:13 PM
 */
public class Main {
    public static void main(String[] args) {
        Coffee c = new BasicCoffee();
        c = new MilkDecorator(c);
        c = new SugarDecorator(c);

        System.out.println(c.getDescription());
        System.out.println("Cost: " + c.cost());
    }
}