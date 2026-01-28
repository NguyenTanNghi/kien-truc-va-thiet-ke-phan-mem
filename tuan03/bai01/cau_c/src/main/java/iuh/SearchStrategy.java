package iuh;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Strategy Pattern - Chiến lược tìm kiếm sách
 */
public interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
}
