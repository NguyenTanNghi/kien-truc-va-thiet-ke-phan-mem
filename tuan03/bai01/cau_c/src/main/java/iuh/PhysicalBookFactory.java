package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 */
public class PhysicalBookFactory extends BookFactory {
    @Override
    public Book createBook(String id, String title, String author, double price, String genre,
            String... additionalInfo) {
        String shelfLocation = additionalInfo.length > 0 ? additionalInfo[0] : "A-01";
        return new PhysicalBook(id, title, author, price, genre, shelfLocation);
    }
}
