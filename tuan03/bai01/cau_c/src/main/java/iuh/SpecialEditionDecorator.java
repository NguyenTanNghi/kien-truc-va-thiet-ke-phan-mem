package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Decorator - Phiên bản đặc biệt (có chữ ký tác giả)
 */
public class SpecialEditionDecorator extends BookDecorator {
    private String authorSignature;

    public SpecialEditionDecorator(Book book, String authorSignature) {
        super(book);
        this.authorSignature = authorSignature;
    }

    @Override
    public String toString() {
        return decoratedBook.toString() + " [PHIÊN BẢN ĐẶC BIỆT - Có chữ ký: " + authorSignature + "]";
    }

    @Override
    public double getPrice() {
        return decoratedBook.getPrice() * 1.5; // Giá tăng 50%
    }

    public String getAuthorSignature() {
        return authorSignature;
    }
}
