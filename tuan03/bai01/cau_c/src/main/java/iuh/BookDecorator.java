package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Decorator Pattern - Decorator cơ bản cho sách
 */
public abstract class BookDecorator extends Book {
    protected Book decoratedBook;

    public BookDecorator(Book book) {
        super(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getGenre());
        this.decoratedBook = book;
        this.isAvailable = book.isAvailable();
    }

    @Override
    public String getBookType() {
        return decoratedBook.getBookType();
    }

    @Override
    public String toString() {
        return decoratedBook.toString();
    }
}
