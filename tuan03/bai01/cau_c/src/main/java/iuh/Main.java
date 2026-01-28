package iuh;

import java.util.List;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:56 PM
 * Ứng dụng quản lý thư viện sử dụng 5 Design Patterns:
 * 1. Singleton Pattern - Quản lý Library
 * 2. Factory Method Pattern - Tạo các loại sách
 * 3. Strategy Pattern - Tìm kiếm sách
 * 4. Observer Pattern - Thông báo mượn/trả sách
 * 5. Decorator Pattern - Mở rộng tính năng sách
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║     HỆ THỐNG QUẢN LÝ THƯ VIỆN - DESIGN PATTERNS          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // 1. SINGLETON PATTERN - Lấy instance duy nhất của Library
        System.out.println(">>> 1. SINGLETON PATTERN - Khởi tạo thư viện");
        Library library = Library.getInstance();
        Library library2 = Library.getInstance();
        System.out.println("✓ Library instance 1: " + library);
        System.out.println("✓ Library instance 2: " + library2);
        System.out.println("✓ Hai instance giống nhau? " + (library == library2));
        System.out.println();

        // 2. OBSERVER PATTERN - Đăng ký người quan sát
        System.out.println(">>> 2. OBSERVER PATTERN - Đăng ký người quan sát");
        LibraryStaff staff1 = new LibraryStaff("Nguyễn Văn A");
        LibraryStaff staff2 = new LibraryStaff("Trần Thị B");
        RegisteredUser user1 = new RegisteredUser("Lê Văn C", "levanc@email.com");
        RegisteredUser user2 = new RegisteredUser("Phạm Thị D", "phamthid@email.com");

        library.addObserver(staff1);
        library.addObserver(staff2);
        library.addObserver(user1);
        library.addObserver(user2);
        System.out.println();

        // 3. FACTORY METHOD PATTERN - Thêm sách vào thư viện
        System.out.println(">>> 3. FACTORY METHOD PATTERN - Thêm sách vào thư viện");
        
        // Thêm sách giấy
        library.addBook("physical", "B001", "Lập trình Java cơ bản", 
                       "Nguyễn Văn A", 150000, "Công nghệ", "Kệ A-01");
        library.addBook("physical", "B002", "Thiết kế hệ thống", 
                       "Trần Văn B", 200000, "Công nghệ", "Kệ A-02");
        
        // Thêm sách điện tử
        library.addBook("ebook", "E001", "AI và Machine Learning", 
                       "Lê Thị C", 100000, "Trí tuệ nhân tạo", 
                       "http://library.com/ebooks/E001", "12.5");
        library.addBook("ebook", "E002", "Cloud Computing", 
                       "Phạm Văn D", 120000, "Công nghệ", 
                       "http://library.com/ebooks/E002", "8.3");
        
        // Thêm tiểu thuyết
        library.addBook("novel", "N001", "Dế Mèn phiêu lưu ký", 
                       "Tô Hoài", 80000, "Văn học thiếu nhi", "250");
        library.addBook("novel", "N002", "Số Đỏ", 
                       "Vũ Trọng Phụng", 95000, "Văn học hiện đại", "320");
        
        System.out.println();

        // Hiển thị tất cả sách
        library.displayAllBooks();

        // 4. STRATEGY PATTERN - Tìm kiếm sách với các chiến lược khác nhau
        System.out.println(">>> 4. STRATEGY PATTERN - Tìm kiếm sách");
        
        // Tìm kiếm theo tên
        System.out.println("\n--- Tìm kiếm theo TÊN (keyword: 'Java') ---");
        library.setSearchStrategy(new SearchByTitle());
        List<Book> results = library.searchBooks("Java");
        library.displaySearchResults(results);

        // Tìm kiếm theo thể loại
        System.out.println("--- Tìm kiếm theo THỂ LOẠI (keyword: 'Công nghệ') ---");
        library.setSearchStrategy(new SearchByGenre());
        results = library.searchBooks("Công nghệ");
        library.displaySearchResults(results);

        // Tìm kiếm theo giá (tối đa)
        System.out.println("--- Tìm kiếm theo GIÁ (tối đa: 100000) ---");
        library.setSearchStrategy(new SearchByPrice());
        results = library.searchBooks("100000");
        library.displaySearchResults(results);

        // 5. OBSERVER PATTERN - Mượn và trả sách (sẽ thông báo cho observers)
        System.out.println(">>> 5. OBSERVER PATTERN - Mượn và trả sách");
        System.out.println("\n--- Mượn sách ---");
        library.borrowBook("B001", "Lê Văn C");
        System.out.println();
        
        library.borrowBook("E001", "Phạm Thị D");
        System.out.println();

        System.out.println("\n--- Trả sách ---");
        library.returnBook("B001");
        System.out.println();

        // 6. DECORATOR PATTERN - Mở rộng tính năng sách
        System.out.println("\n>>> 6. DECORATOR PATTERN - Mở rộng tính năng sách");
        
        // Lấy một cuốn sách từ thư viện
        Book originalBook = library.getBooks().get(0);
        System.out.println("\nSách gốc:");
        System.out.println(originalBook);
        System.out.println("Giá: " + String.format("%.0f VND", originalBook.getPrice()));

        // Decorator 1: Phiên bản đặc biệt
        System.out.println("\n--- Thêm tính năng: Phiên bản đặc biệt ---");
        Book specialBook = new SpecialEditionDecorator(originalBook, "Chữ ký Nguyễn Văn A");
        System.out.println(specialBook);
        System.out.println("Giá sau khi thêm: " + String.format("%.0f VND", specialBook.getPrice()));

        // Decorator 2: Gói quà
        System.out.println("\n--- Thêm tính năng: Gói quà ---");
        Book giftBook = new GiftWrapDecorator(originalBook, "Chúc mừng sinh nhật!");
        System.out.println(giftBook);
        System.out.println("Giá sau khi thêm: " + String.format("%.0f VND", giftBook.getPrice()));

        // Decorator 3: Đánh dấu trang
        System.out.println("\n--- Thêm tính năng: Đánh dấu trang ---");
        Book bookmarkBook = new BookmarkDecorator(originalBook, "cao cấp");
        System.out.println(bookmarkBook);
        System.out.println("Giá sau khi thêm: " + String.format("%.0f VND", bookmarkBook.getPrice()));

        // Decorator kết hợp nhiều tính năng
        System.out.println("\n--- Kết hợp nhiều decorator ---");
        Book premiumBook = new SpecialEditionDecorator(originalBook, "Chữ ký Nguyễn Văn A");
        premiumBook = new GiftWrapDecorator(premiumBook, "Tặng bạn yêu quý!");
        premiumBook = new BookmarkDecorator(premiumBook, "vàng 24k");
        System.out.println(premiumBook);
        System.out.println("Giá cuối cùng: " + String.format("%.0f VND", premiumBook.getPrice()));

        // Hiển thị danh sách sách cuối cùng
        System.out.println("\n>>> TRẠNG THÁI CUỐI CÙNG CỦA THƯ VIỆN");
        library.displayAllBooks();

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║              HOÀN THÀNH DEMO HỆ THỐNG                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }
}