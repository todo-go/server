package kwonyonghoon.todogo.dto;

import kwonyonghoon.todogo.task.Task;
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

    public Task toEntity(){
        return Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .build();
    }
}
