package kwonyonghoon.todogo.user;

import jakarta.transaction.Transactional;
import kwonyonghoon.todogo.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @DisplayName("signup: 회원가입에 성공한다.")
    @Test
    void signup(){
        // given
        String phoneNumber = "010-1234-5678";
        String name = "홍길동";

        // when
        UserResponse response = userService.registerUser(phoneNumber, name);

        // then
        assertNotNull(response);
        assertEquals(phoneNumber, response.getPhoneNumber());
        assertEquals(name, response.getName());
    }

}