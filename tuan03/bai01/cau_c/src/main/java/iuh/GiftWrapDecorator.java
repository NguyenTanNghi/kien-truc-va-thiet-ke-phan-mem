package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Decorator - Gói quà
 */
public class GiftWrapDecorator extends BookDecorator {
    private String giftMessage;

    public GiftWrapDecorator(Book book, String giftMessage) {
        super(book);
        this.giftMessage = giftMessage;
    }

    @Override
    public String toString() {
        return decoratedBook.toString() + " [GÓI QUÀ - Lời nhắn: \"" + giftMessage + "\"]";
    }

    @Override
    public double getPrice() {
        return decoratedBook.getPrice() + 15000; // Phí gói quà 15,000 VND
    }

    public String getGiftMessage() {
        return giftMessage;
    }
}
