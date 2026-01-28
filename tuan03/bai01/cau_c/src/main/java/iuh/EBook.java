package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Sách điện tử
 */
public class EBook extends Book {
    private String downloadUrl;
    private double fileSize; // MB

    public EBook(String id, String title, String author, double price, String genre, String downloadUrl,
            double fileSize) {
        super(id, title, author, price, genre);
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
    }

    @Override
    public String getBookType() {
        return "Sách điện tử";
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public double getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Dung lượng: %.2f MB", fileSize);
    }
}
