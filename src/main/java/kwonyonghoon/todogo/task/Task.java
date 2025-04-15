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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Task(String title, String description, LocalDateTime deadline, Boolean status, User user) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.user = user;
    }

    public void update(String title, String description, LocalDateTime deadline, Boolean status) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }
}
