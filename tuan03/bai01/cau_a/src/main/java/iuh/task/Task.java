package iuh.task;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:43 PM
 */
import iuh.core.Observer;
import iuh.core.Subject;

import java.util.ArrayList;
import java.util.List;

public class Task implements Subject<TaskUpdate> {
    private final String id;
    private final String title;
    private TaskStatus status;
    private int progress; // 0..100

    private final List<Observer<TaskUpdate>> observers = new ArrayList<>();

    public Task(String id, String title) {
        this.id = id;
        this.title = title;
        this.status = TaskStatus.TODO;
        this.progress = 0;
    }

    @Override
    public void attach(Observer<TaskUpdate> observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer<TaskUpdate> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(TaskUpdate data) {
        for (Observer<TaskUpdate> o : observers) {
            o.update(data);
        }
    }

    public void setStatus(TaskStatus newStatus) {
        if (newStatus == this.status) return;

        TaskStatus old = this.status;
        int oldProgress = this.progress;

        this.status = newStatus;

        notifyObservers(new TaskUpdate(id, title, old, newStatus, oldProgress, this.progress));
    }

    public void setProgress(int newProgress) {
        if (newProgress < 0) newProgress = 0;
        if (newProgress > 100) newProgress = 100;
        if (newProgress == this.progress) return;

        TaskStatus oldStatus = this.status;
        int oldProgress = this.progress;

        this.progress = newProgress;

        // ví dụ rule: 100% -> DONE
        if (this.progress == 100) {
            this.status = TaskStatus.DONE;
        } else if (this.status == TaskStatus.TODO && this.progress > 0) {
            this.status = TaskStatus.IN_PROGRESS;
        }

        notifyObservers(new TaskUpdate(id, title, oldStatus, this.status, oldProgress, this.progress));
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return status; }
    public int getProgress() { return progress; }
}