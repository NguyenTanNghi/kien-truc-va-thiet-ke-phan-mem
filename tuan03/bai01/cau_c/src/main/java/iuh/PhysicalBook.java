package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Sách giấy
 */
public class PhysicalBook extends Book {
    private String shelfLocation;

    public PhysicalBook(String id, String title, String author, double price, String genre, String shelfLocation) {
        super(id, title, author, price, genre);
        this.shelfLocation = shelfLocation;
    }

    @Override
    public String getBookType() {
        return "Sách giấy";
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    @Override
    public String toString() {
        return super.toString() + " | Vị trí: " + shelfLocation;
    }
}
