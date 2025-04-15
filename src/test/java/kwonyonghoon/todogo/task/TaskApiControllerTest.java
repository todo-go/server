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
        final String url = "/api/tasks/{id}";
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
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedTask.getId()));

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
        final String url = "/api/tasks/{id}";
        final String title = "title";
        final String description = "description";
        final LocalDateTime deadline = LocalDateTime.now();
        final Boolean status = false;

        Task savedTask = taskRepository.save(Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .build());

        // when
        mockMvc.perform(delete(url, savedTask.getId()))
                .andExpect(status().isOk());

        // then
        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks.size()).isEqualTo(0);
    }

    @DisplayName("updateTask: 데이터 수정에 성공한다.")
    @Test
    public void updateTask() throws Exception {
        // given
        final String url = "/api/tasks/{id}";
        final String title = "title";
        final String description = "description";
        final LocalDateTime deadline = LocalDateTime.now();
        final Boolean status = false;

        Task savedTask = taskRepository.save(Task.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .build());

        final String newTitle = "new title";
        final String newDescription = "new description";
        final LocalDateTime newDeadline = LocalDateTime.now();
        final Boolean newStatus = true;

        UpdateTaskRequest request = new UpdateTaskRequest(newTitle, newDescription, newDeadline, newStatus);

        // when
        ResultActions result = mockMvc.perform(put(url, savedTask.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Task task = taskRepository.findById(savedTask.getId()).get();

        assertThat(task.getTitle()).isEqualTo(newTitle);
        assertThat(task.getDescription()).isEqualTo(newDescription);
        assertThat(task.getStatus()).isEqualTo(newStatus);
    }
}