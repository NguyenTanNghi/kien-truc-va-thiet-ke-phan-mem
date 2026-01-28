package iuh.stock;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:42 PM
 */

import iuh.core.Observer;
import iuh.core.Subject;

import java.util.ArrayList;
import java.util.List;

public class Stock implements Subject<StockUpdate> {
    private final String symbol;
    private double price;
    private final List<Observer<StockUpdate>> observers = new ArrayList<>();

    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }

    @Override
    public void attach(Observer<StockUpdate> observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer<StockUpdate> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(StockUpdate data) {
        for (Observer<StockUpdate> o : observers) {
            o.update(data);
        }
    }

    public void setPrice(double newPrice) {
        if (newPrice == this.price) return;

        double old = this.price;
        this.price = newPrice;

        notifyObservers(new StockUpdate(symbol, old, newPrice));
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
}
