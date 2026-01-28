package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Observer Pattern - Người quan sát thư viện
 */
public interface LibraryObserver {
    void update(String event, Book book, String borrower);
}
