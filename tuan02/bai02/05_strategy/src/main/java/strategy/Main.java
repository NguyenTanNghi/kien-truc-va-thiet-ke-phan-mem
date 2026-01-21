package strategy;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/21/2026 2:11 PM
 */
public class Main {
    public static void main(String[] args) {
        SortContext ctx = new SortContext();

        ctx.setStrategy(new BubbleSort());
        ctx.execute();

        ctx.setStrategy(new QuickSort());
        ctx.execute();
    }
}