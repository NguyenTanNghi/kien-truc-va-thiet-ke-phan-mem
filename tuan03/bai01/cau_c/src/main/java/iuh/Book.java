package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 */
public abstract class Book {
    protected String id;
    protected String title;
    protected String author;
    protected double price;
    protected String genre;
    protected boolean isAvailable;

    public Book(String id, String title, String author, double price, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.isAvailable = true;
    }

    public abstract String getBookType();

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Tác giả: %s | Giá: %.2f | Thể loại: %s | %s",
                id, getBookType(), title, author, price, genre,
                isAvailable ? "Còn sách" : "Đã mượn");
    }
}
