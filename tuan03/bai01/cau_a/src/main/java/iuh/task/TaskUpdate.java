package iuh.task;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:43 PM
 */
public class TaskUpdate {
    private final String taskId;
    private final String title;
    private final TaskStatus oldStatus;
    private final TaskStatus newStatus;
    private final int oldProgress;
    private final int newProgress;

    public TaskUpdate(String taskId, String title,
                      TaskStatus oldStatus, TaskStatus newStatus,
                      int oldProgress, int newProgress) {
        this.taskId = taskId;
        this.title = title;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.oldProgress = oldProgress;
        this.newProgress = newProgress;
    }

    public String getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public TaskStatus getOldStatus() { return oldStatus; }
    public TaskStatus getNewStatus() { return newStatus; }
    public int getOldProgress() { return oldProgress; }
    public int getNewProgress() { return newProgress; }
}