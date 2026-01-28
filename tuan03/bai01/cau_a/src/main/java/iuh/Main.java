package iuh;

import iuh.stock.Investor;
import iuh.stock.Stock;
import iuh.task.Task;
import iuh.task.TaskStatus;
import iuh.task.TeamMember;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:39 PM
 */
public class Main {
    public static void main(String[] args) {

        // ===== CASE A: Stock =====
        System.out.println("=== CASE A: STOCK ===");
        Stock apple = new Stock("AAPL", 100.0);

        Investor i1 = new Investor("An");
        Investor i2 = new Investor("Binh");

        apple.attach(i1);
        apple.attach(i2);

        apple.setPrice(101.5);
        apple.setPrice(99.9);

        apple.detach(i2);
        apple.setPrice(120.0);

        // ===== CASE B: Task =====
        System.out.println("\n=== CASE B: TASK ===");
        Task task = new Task("T-01", "Implement Login API");

        TeamMember m1 = new TeamMember("Nghi");
        TeamMember m2 = new TeamMember("Khoa");

        task.attach(m1);
        task.attach(m2);

        task.setProgress(10);
        task.setStatus(TaskStatus.BLOCKED);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setProgress(100);
    }
}