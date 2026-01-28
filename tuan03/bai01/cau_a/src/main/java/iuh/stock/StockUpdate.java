package iuh.stock;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:42 PM
 */
public class StockUpdate {
    private final String symbol;
    private final double oldPrice;
    private final double newPrice;

    public StockUpdate(String symbol, double oldPrice, double newPrice) {
        this.symbol = symbol;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public String getSymbol() { return symbol; }
    public double getOldPrice() { return oldPrice; }
    public double getNewPrice() { return newPrice; }
}