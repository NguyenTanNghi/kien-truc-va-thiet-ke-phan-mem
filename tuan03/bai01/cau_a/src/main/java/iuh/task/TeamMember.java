package iuh.task;

import iuh.core.Observer;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:43 PM
 */
public class TeamMember implements Observer<TaskUpdate> {
    private final String name;

    public TeamMember(String name) {
        this.name = name;
    }

    @Override
    public void update(TaskUpdate data) {
        System.out.printf(
                "🧑‍💻 Member %s nhận thông báo Task[%s - %s]: Status %s -> %s | Progress %d%% -> %d%%%n",
                name,
                data.getTaskId(),
                data.getTitle(),
                data.getOldStatus(), data.getNewStatus(),
                data.getOldProgress(), data.getNewProgress()
        );
    }
}