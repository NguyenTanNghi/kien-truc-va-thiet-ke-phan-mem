package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:24 PM
 */
public class Product {
    private final String name;
    private final double price;
    private TaxStrategy taxStrategy;

    public Product(String name, double price, TaxStrategy taxStrategy) {
        this.name = name;
        this.price = price;
        this.taxStrategy = taxStrategy;
    }

    public void setTaxStrategy(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public void printBill() {
        double tax = taxStrategy.calcTax(price);
        double total = price + tax;

        System.out.println("Product: " + name);
        System.out.println("Price: " + price);
        System.out.println("Tax type: " + taxStrategy.name());
        System.out.println("Tax: " + tax);
        System.out.println("Total: " + total);
        System.out.println("----------------------");
    }
}