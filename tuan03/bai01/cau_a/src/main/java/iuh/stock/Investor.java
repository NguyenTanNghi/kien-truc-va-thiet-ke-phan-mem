package iuh.stock;

import iuh.core.Observer;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:42 PM
 */
public class Investor implements Observer<StockUpdate> {
    private final String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(StockUpdate data) {
        System.out.printf("📈 Investor %s nhận thông báo: %s đổi giá %.2f -> %.2f%n",
                name, data.getSymbol(), data.getOldPrice(), data.getNewPrice());
    }
}