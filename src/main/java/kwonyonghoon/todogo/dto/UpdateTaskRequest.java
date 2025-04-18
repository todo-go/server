package kwonyonghoon.todogo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateTaskRequest {
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Boolean status;
    private Long userId;
}
