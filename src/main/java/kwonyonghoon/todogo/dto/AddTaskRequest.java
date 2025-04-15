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

    public String title;
    public String description;
    public LocalDateTime deadline;
    public Boolean status;
    public Long userId;

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
