package kwonyonghoon.todogo.user;

import jakarta.transaction.Transactional;
import kwonyonghoon.todogo.dto.UserRegistrationResult;
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
    @Autowired
    private UserRepository userRepository;

    @DisplayName("signup: 회원가입에 성공한다.")
    @Test
    void signup(){
        // given
        String phoneNumber = "010-1234-5680";
        String name = "홍길동";

        // when
        UserRegistrationResult result = userService.registerUser(phoneNumber, name);

        // then
        assertNotNull(result);
        assertTrue(result.isNewUser());

        UserResponse response = result.getUserResponse();
        assertNotNull(response);
        assertEquals(phoneNumber, response.getPhoneNumber());
        assertEquals(name, response.getName());
    }

    @DisplayName("signup: 이미 등록된 전화번호면 기존 유저를 반환한다.")
    @Test
    void signup_existingPhoneNumber(){
        // given
        String phoneNumber = "010-1234-5680";
        String name = "홍길동";

        // 일단 가입
        userService.registerUser(phoneNumber, name);

        // when(두번째 가입)
        UserRegistrationResult result = userService.registerUser(phoneNumber, "홍길길동");

        // then
        assertNotNull(result);
        assertFalse(result.isNewUser()); // 기존 유저인가

        UserResponse response = result.getUserResponse();
        assertNotNull(response);
        assertEquals(phoneNumber, response.getPhoneNumber());
        assertEquals(name, response.getName()); // 원래 등록한 이름인가
    }

}