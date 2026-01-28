package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Decorator - Đánh dấu trang cao cấp
 */
public class BookmarkDecorator extends BookDecorator {
    private String bookmarkType;

    public BookmarkDecorator(Book book, String bookmarkType) {
        super(book);
        this.bookmarkType = bookmarkType;
    }

    @Override
    public String toString() {
        return decoratedBook.toString() + " [Kèm đánh dấu trang " + bookmarkType + "]";
    }

    @Override
    public double getPrice() {
        return decoratedBook.getPrice() + 5000; // Phí đánh dấu trang 5,000 VND
    }

    public String getBookmarkType() {
        return bookmarkType;
    }
}
