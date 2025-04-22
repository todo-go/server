package kwonyonghoon.todogo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import kwonyonghoon.todogo.dto.AddTaskRequest;
import kwonyonghoon.todogo.dto.UpdateTaskRequest;
import kwonyonghoon.todogo.user.User;
import kwonyonghoon.todogo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskService taskService;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("addTask: 데이터 추가에 성공한다.")
    @Test
    public void addTask() throws Exception {
        // given
        final String url = "/api/tasks";
        final String title = "title";
        final String description = "description";
        final LocalDateTime deadline = LocalDateTime.now();
        final Boolean status = false;
        final User user = userRepository.save(User.builder()
                .phoneNumber("010-4072-7941")
                .name("스포는범죄다")
                .build());

        final AddTaskRequest userRequest = new AddTaskRequest(title, description, deadline, status, user.getId());

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo(title);
        assertThat(tasks.get(0).getDescription()).isEqualTo(description);
        assertThat(tasks.get(0).getStatus()).isEqualTo(status);
        assertThat(tasks.get(0).getUser().getId()).isEqualTo(user.getId());
        assertThat(tasks.get(0).getId().getTaskNumber()).isEqualTo(1);
    }

    @DisplayName("findAllTasks: 데이터 목록 조회에 성공한다.")
    @Test
    public void findAllTasks() throws Exception {
        // given
        final String url = "/api/tasks";
        final String title = "title";
        final String description = "description";
        final LocalDateTime deadline = LocalDateTime.now();
        final Boolean status = false;
        final User user = userRepository.save(User.builder()
                .phoneNumber("010-4072-7941")
                .name("스포는범죄다")
                .build());

        taskRepository.save(Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .user(user)
                .taskNumber(taskService.getNextTaskNumber(user))
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value(description))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findTask: 데이터 조회에 성공한다.")
    @Test
    public void findTask() throws Exception {
        // given
        final String url = "/api/users/{userId}/tasks/{taskNumber}";
        final String title = "title";
        final String description = "description";
        final LocalDateTime deadline = LocalDateTime.now();
        final Boolean status = false;
        final User user = userRepository.save(User.builder()
                .phoneNumber("010-4072-7942")
                .name("스포는범죄다")
                .build());

        Task savedTask = taskRepository.save(Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .user(user)
                .taskNumber(taskService.getNextTaskNumber(user))
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, user.getId(), savedTask.getId().getTaskNumber()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.title").value(title));

    }

    @DisplayName("deleteTask: 데이터 삭제에 성공한다.")
    @Test
    public void deleteTask() throws Exception {
        // given
        final String url = "/api/users/{userId}/tasks/{taskNumber}";
        final String title = "title";
        final String description = "description";
        final LocalDateTime deadline = LocalDateTime.now();
        final Boolean status = false;
        final User user = userRepository.save(User.builder()
                .phoneNumber("010-4072-7941")
                .name("스포는범죄다")
                .build());

        Task savedTask = taskRepository.save(Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .user(user)
                .taskNumber(taskService.getNextTaskNumber(user))
                .build());

        // when
        mockMvc.perform(delete(url, user.getId(), savedTask.getId().getTaskNumber()))
                .andExpect(status().isOk());

        // then
        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks.size()).isEqualTo(0);
    }

    @DisplayName("updateTask: 데이터 수정에 성공한다.")
    @Test
    public void updateTask() throws Exception {
        // given
        final String url = "/api/users/{userId}/tasks/{taskNumber}";
        final String title = "title";
        final String description = "description";
        final LocalDateTime deadline = LocalDateTime.now();
        final Boolean status = false;
        final User user = userRepository.save(User.builder()
                .phoneNumber("010-4072-7941")
                .name("스포는범죄다")
                .build());

        Task savedTask = taskRepository.save(Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .user(user)
                .taskNumber(taskService.getNextTaskNumber(user))
                .build());

        final String newTitle = "new title";
        final String newDescription = "new description";
        final LocalDateTime newDeadline = LocalDateTime.now();
        final Boolean newStatus = true;

        UpdateTaskRequest request = new UpdateTaskRequest(newTitle, newDescription, newDeadline, newStatus, user.getId());

        // when
        ResultActions result = mockMvc.perform(put(url, user.getId(), savedTask.getId().getTaskNumber())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Task task = taskRepository.findById(savedTask.getId()).get();

        assertThat(task.getTitle()).isEqualTo(newTitle);
        assertThat(task.getDescription()).isEqualTo(newDescription);
        assertThat(task.getStatus()).isEqualTo(newStatus);
    }

    @DisplayName("findTasksByUser: 특정 유저의 Task 목록 조회에 성공한다.")
    @Test
    public void findTasksByUser() throws Exception {
        // given
        final String url = "/api/tasks/user/{userId}";
        final User user1 = userRepository.save(User.builder()
                .phoneNumber("010-0000-0001")
                .name("사용자1")
                .build());

        final User user2 = userRepository.save(User.builder()
                .phoneNumber("010-0000-0002")
                .name("사용자2")
                .build());

        taskRepository.save(Task.builder()
                .title("User1 Task1")
                .description("description")
                .deadline(LocalDateTime.now())
                .status(false)
                .user(user1)
                .taskNumber(taskService.getNextTaskNumber(user1))
                .build());

        taskRepository.save(Task.builder()
                .title("User2 Task1")
                .description("description")
                .deadline(LocalDateTime.now())
                .status(false)
                .user(user2)
                .taskNumber(taskService.getNextTaskNumber(user2))
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, user1.getUuid())
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("User1 Task1"));
    }

    @DisplayName("addTask: 사용자별로 ID가 1부터 시작한다.")
    @Test
    public void addTaskForDifferentUsers() throws Exception {
        // 사용자 1 생성
        final User user1 = userRepository.save(User.builder()
                .phoneNumber("010-0000-0001")
                .name("사용자1")
                .build());

        // 사용자 2 생성
        final User user2 = userRepository.save(User.builder()
                .phoneNumber("010-0000-0002")
                .name("사용자2")
                .build());

        // 사용자 1의 첫 번째 Task 추가
        final AddTaskRequest taskRequest1 = new AddTaskRequest("Task1", "Description1", LocalDateTime.now(), false, user1.getId());
        final String requestBody1 = objectMapper.writeValueAsString(taskRequest1);
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody1))
                .andExpect(status().isCreated());

        // 사용자 2의 첫 번째 Task 추가
        final AddTaskRequest taskRequest2 = new AddTaskRequest("Task2", "Description2", LocalDateTime.now(), false, user2.getId());
        final String requestBody2 = objectMapper.writeValueAsString(taskRequest2);
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody2))
                .andExpect(status().isCreated());

        // 사용자 1의 두 번째 Task 추가
        final AddTaskRequest taskRequest3 = new AddTaskRequest("Task3", "Description3", LocalDateTime.now(), false, user1.getId());
        final String requestBody3 = objectMapper.writeValueAsString(taskRequest3);
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody3))
                .andExpect(status().isCreated());

        // 사용자 2의 두 번째 Task 추가
        final AddTaskRequest taskRequest4 = new AddTaskRequest("Task4", "Description4", LocalDateTime.now(), false, user2.getId());
        final String requestBody4 = objectMapper.writeValueAsString(taskRequest4);
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody4))
                .andExpect(status().isCreated());

        // 사용자 1의 Task 목록 조회
        final List<Task> tasksByUser1 = taskRepository.findAllByUser(user1);
        // 사용자 2의 Task 목록 조회
        final List<Task> tasksByUser2 = taskRepository.findAllByUser(user2);

        // 사용자 1의 Task ID가 1부터 시작하는지 확인
        assertThat(tasksByUser1.get(0).getId().getTaskNumber()).isEqualTo(1);
        assertThat(tasksByUser1.get(1).getId().getTaskNumber()).isEqualTo(2);

        // 사용자 2의 Task ID가 1부터 시작하는지 확인
        assertThat(tasksByUser2.get(0).getId().getTaskNumber()).isEqualTo(1);
        assertThat(tasksByUser2.get(1).getId().getTaskNumber()).isEqualTo(2);
    }

}