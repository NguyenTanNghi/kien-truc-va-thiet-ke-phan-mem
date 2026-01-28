package iuh;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 *          Người dùng đã đăng ký - Observer
 */
public class RegisteredUser implements LibraryObserver {
    private String name;
    private String email;

    public RegisteredUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public void update(String event, Book book, String borrower) {
        System.out.println("[NGƯỜI DÙNG " + name + "] Email: " + email +
                " - Thông báo: " + event + " - Sách: " + book.getTitle());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
