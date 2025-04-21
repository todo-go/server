package kwonyonghoon.todogo.task;

import kwonyonghoon.todogo.dto.AddTaskRequest;
import kwonyonghoon.todogo.dto.TaskResponse;
import kwonyonghoon.todogo.dto.UpdateTaskRequest;
import kwonyonghoon.todogo.user.UserRepository;
import kwonyonghoon.todogo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TaskApiController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping("/api/tasks")
    public ResponseEntity<TaskResponse> addTask(@RequestBody AddTaskRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Task task = request.toEntity(user);
        Task savedTask = taskService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TaskResponse(savedTask));
    }

    @GetMapping("/api/tasks")
    public ResponseEntity<List<TaskResponse>> findAllTasks(){
        List<TaskResponse> tasks = taskService.findAll()
                .stream()
                .map(TaskResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(tasks);
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponse> findTaskById(@PathVariable Long id){
        Task task = taskService.findById(id);

        return ResponseEntity.ok()
                .body(new TaskResponse(task));
    }

    @DeleteMapping("/api/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request){
        Task updatedTask = taskService.update(id, request);

        return ResponseEntity.ok()
                .body(new TaskResponse(updatedTask));
    }

    @GetMapping("/api/tasks/user/{uuid}")
    public ResponseEntity<List<TaskResponse>> findTasksByUserUuid(@PathVariable String uuid){
        List<TaskResponse> tasks = taskService.findAllByUserUuid(uuid)
                .stream()
                .map(TaskResponse::new)
                .toList();

        return ResponseEntity.ok().body(tasks);
    }
}
