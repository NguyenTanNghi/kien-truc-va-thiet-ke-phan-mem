package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Factory Method Pattern - Tạo các loại sách khác nhau
 */
public abstract class BookFactory {
    public abstract Book createBook(String id, String title, String author, double price, String genre,
            String... additionalInfo);

    public static BookFactory getFactory(String bookType) {
        switch (bookType.toLowerCase()) {
            case "physical":
                return new PhysicalBookFactory();
            case "ebook":
                return new EBookFactory();
            case "novel":
                return new NovelFactory();
            default:
                throw new IllegalArgumentException("Loại sách không hợp lệ: " + bookType);
        }
    }
}
