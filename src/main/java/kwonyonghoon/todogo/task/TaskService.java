package kwonyonghoon.todogo.task;

import jakarta.transaction.Transactional;
import kwonyonghoon.todogo.dto.AddTaskRequest;
import kwonyonghoon.todogo.dto.UpdateTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public Task save(AddTaskRequest request){
        return taskRepository.save(request.toEntity());
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
