package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 */
public class EBookFactory extends BookFactory {
    @Override
    public Book createBook(String id, String title, String author, double price, String genre,
            String... additionalInfo) {
        String downloadUrl = additionalInfo.length > 0 ? additionalInfo[0] : "http://library.com/ebooks/" + id;
        double fileSize = additionalInfo.length > 1 ? Double.parseDouble(additionalInfo[1]) : 5.0;
        return new EBook(id, title, author, price, genre, downloadUrl, fileSize);
    }
}
