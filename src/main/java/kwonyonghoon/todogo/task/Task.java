package kwonyonghoon.todogo.task;

import jakarta.persistence.*;
import kwonyonghoon.todogo.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @EmbeddedId
    private TaskId id;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Task(User user, Long taskNumber, String title, String description, LocalDateTime deadline, Boolean status) {
        this.id = new TaskId(user.getId(), taskNumber);
        this.user = user;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public void update(String title, String description, LocalDateTime deadline, Boolean status) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }
}
