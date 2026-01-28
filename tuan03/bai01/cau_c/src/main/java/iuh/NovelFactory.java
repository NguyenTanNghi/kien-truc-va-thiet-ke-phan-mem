package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 */
public class NovelFactory extends BookFactory {
    @Override
    public Book createBook(String id, String title, String author, double price, String genre,
            String... additionalInfo) {
        int numberOfPages = additionalInfo.length > 0 ? Integer.parseInt(additionalInfo[0]) : 300;
        return new Novel(id, title, author, price, genre, numberOfPages);
    }
}
