package kwonyonghoon.todogo.task;

import jakarta.transaction.Transactional;
import kwonyonghoon.todogo.dto.AddTaskRequest;
import kwonyonghoon.todogo.dto.UpdateTaskRequest;
import kwonyonghoon.todogo.user.User;
import kwonyonghoon.todogo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task save(AddTaskRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("User not found"));

        return taskRepository.save(request.toEntity(user));
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task update(Long id, UpdateTaskRequest request){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        task.update(request.getTitle(), request.getDescription(), request.getDeadline(), request.getStatus());

        return task;
    }

}
