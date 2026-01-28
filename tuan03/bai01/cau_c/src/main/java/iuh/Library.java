package iuh;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Singleton Pattern - Quản lý thư viện (đảm bảo chỉ có 1 instance duy
 *          nhất)
 */
public class Library {
    private static Library instance;
    private List<Book> books;
    private List<LibraryObserver> observers;
    private SearchStrategy searchStrategy;

    private Library() {
        this.books = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.searchStrategy = new SearchByTitle(); // Default strategy
    }

    public static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    // Observer Pattern - Quản lý observers
    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
        System.out.println("✓ Đã thêm người quan sát: " +
                (observer instanceof LibraryStaff ? ((LibraryStaff) observer).getName()
                        : ((RegisteredUser) observer).getName()));
    }

    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String event, Book book, String borrower) {
        for (LibraryObserver observer : observers) {
            observer.update(event, book, borrower);
        }
    }

    // Factory Method Pattern - Thêm sách vào thư viện
    public void addBook(String bookType, String id, String title, String author,
            double price, String genre, String... additionalInfo) {
        BookFactory factory = BookFactory.getFactory(bookType);
        Book book = factory.createBook(id, title, author, price, genre, additionalInfo);
        books.add(book);
        System.out.println("✓ Đã thêm sách: " + book.getTitle());
    }

    // Strategy Pattern - Tìm kiếm sách
    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    public List<Book> searchBooks(String keyword) {
        return searchStrategy.search(books, keyword);
    }

    // Mượn sách
    public boolean borrowBook(String bookId, String borrower) {
        Optional<Book> bookOpt = books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.isAvailable()) {
                book.setAvailable(false);
                notifyObservers("MƯỢN SÁCH", book, borrower);
                System.out.println("✓ Đã mượn sách: " + book.getTitle() + " cho " + borrower);
                return true;
            } else {
                System.out.println("✗ Sách đã được mượn: " + book.getTitle());
                return false;
            }
        } else {
            System.out.println("✗ Không tìm thấy sách với ID: " + bookId);
            return false;
        }
    }

    // Trả sách
    public boolean returnBook(String bookId) {
        Optional<Book> bookOpt = books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (!book.isAvailable()) {
                book.setAvailable(true);
                notifyObservers("TRẢ SÁCH", book, null);
                System.out.println("✓ Đã trả sách: " + book.getTitle());
                return true;
            } else {
                System.out.println("✗ Sách chưa được mượn: " + book.getTitle());
                return false;
            }
        } else {
            System.out.println("✗ Không tìm thấy sách với ID: " + bookId);
            return false;
        }
    }

    // Hiển thị tất cả sách
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("Thư viện chưa có sách nào.");
            return;
        }
        System.out.println("\n========== DANH SÁCH SÁCH TRONG THƯ VIỆN ==========");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("===================================================\n");
    }

    // Hiển thị kết quả tìm kiếm
    public void displaySearchResults(List<Book> results) {
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sách nào.");
            return;
        }
        System.out.println("\n========== KẾT QUẢ TÌM KIẾM ==========");
        for (Book book : results) {
            System.out.println(book);
        }
        System.out.println("Tổng số: " + results.size() + " cuốn");
        System.out.println("=====================================\n");
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }
}
