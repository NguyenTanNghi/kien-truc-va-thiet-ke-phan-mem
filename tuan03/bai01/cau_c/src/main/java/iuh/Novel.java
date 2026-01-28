package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Tiểu thuyết
 */
public class Novel extends Book {
    private int numberOfPages;

    public Novel(String id, String title, String author, double price, String genre, int numberOfPages) {
        super(id, title, author, price, genre);
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String getBookType() {
        return "Tiểu thuyết";
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public String toString() {
        return super.toString() + " | Số trang: " + numberOfPages;
    }
}
