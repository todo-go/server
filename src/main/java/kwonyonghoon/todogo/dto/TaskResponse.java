package kwonyonghoon.todogo.dto;

import kwonyonghoon.todogo.task.Task;
import kwonyonghoon.todogo.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskResponse {

    private final Long userId;
    private final Long taskNumber;
    private final String title;
    private final String description;
    private final LocalDateTime deadline;
    private final Boolean status;
    private final UserResponse user;

    public TaskResponse(Task task) {
        this.userId = task.getId().getUserId();
        this.taskNumber = task.getId().getTaskNumber();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
        this.status = task.getStatus();
        this.user = new UserResponse(task.getUser());
    }
}
