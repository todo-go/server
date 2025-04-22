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

        Long nextTaskNumber = getNextTaskNumber(user);

        if(nextTaskNumber == null){
            throw new IllegalArgumentException("TaskNumber는 Null이 될 수 없습니다");
        }

        Task task = Task.builder().
                title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .status(request.getStatus())
                .user(user)
                .taskNumber(nextTaskNumber)
                .build();

        return taskRepository.save(task);
    }

    public Long getNextTaskNumber(User user){
        Long nextTaskNumber = taskRepository.countByUser(user) + 1;

        if(nextTaskNumber == null){
            throw new IllegalArgumentException("TaskNumber는 Null이 될 수 없습니다");
        }

        return nextTaskNumber;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(TaskId id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(TaskId id){
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task update(TaskId id, UpdateTaskRequest request){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        if(!task.getUser().getId().equals(request.getUserId())){
            throw new IllegalArgumentException("본인이 작성한 Task만 수정할 수 있습니다.");
        }

        task.update(request.getTitle(), request.getDescription(), request.getDeadline(), request.getStatus());

        return task;
    }

    public List<Task> findAllByUserUuid(String uuid){
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 UUID를 가진 유저가 없습니다."));
        return taskRepository.findAllByUser(user);
    }
}
