package kwonyonghoon.todogo.dto;

import kwonyonghoon.todogo.task.Task;
import kwonyonghoon.todogo.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddTaskRequest {

    private String title;
    private String description;
    private LocalDateTime deadline;
    private Boolean status;
    private Long userId;

    public Task toEntity(User user){
        return Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .user(user)
                .build();
    }
}
