package kwonyonghoon.todogo.task;

import kwonyonghoon.todogo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser(User user);
}