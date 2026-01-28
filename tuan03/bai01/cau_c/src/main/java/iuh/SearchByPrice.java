package iuh;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Tìm kiếm theo giá (giá tối đa)
 */
public class SearchByPrice implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        try {
            double maxPrice = Double.parseDouble(keyword);
            return books.stream()
                    .filter(book -> book.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            System.out.println("Giá không hợp lệ: " + keyword);
            return List.of();
        }
    }
}
