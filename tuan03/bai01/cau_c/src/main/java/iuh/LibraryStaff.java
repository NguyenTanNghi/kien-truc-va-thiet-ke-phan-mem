package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Nhân viên thư viện - Observer
 */
public class LibraryStaff implements LibraryObserver {
    private String name;

    public LibraryStaff(String name) {
        this.name = name;
    }

    @Override
    public void update(String event, Book book, String borrower) {
        System.out.println("[NHÂN VIÊN " + name + "] Nhận thông báo: " + event +
                " - Sách: " + book.getTitle() +
                (borrower != null ? " - Người mượn: " + borrower : ""));
    }

    public String getName() {
        return name;
    }
}
