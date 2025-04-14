package kwonyonghoon.todogo.dto;

import kwonyonghoon.todogo.task.Task;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskResponse {

    private final String title;
    private final String description;
    private final LocalDateTime deadline;
    private final Boolean status;

    public TaskResponse(Task task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
        this.status = task.getStatus();
    }
}
