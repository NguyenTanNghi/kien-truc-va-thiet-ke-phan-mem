package iuh;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Tìm kiếm theo tên sách
 */
public class SearchByTitle implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
