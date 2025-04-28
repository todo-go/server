package kwonyonghoon.todogo.user;

import jakarta.transaction.Transactional;
import kwonyonghoon.todogo.dto.UserRegistrationResult;
import kwonyonghoon.todogo.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserRegistrationResult registerUser(String phoneNumber, String name){

        return userRepository.findByPhoneNumber(phoneNumber)
                .map(user -> new UserRegistrationResult(new UserResponse(user), false)) // 기존 유저
                .orElseGet(() -> {
                    User user = User.builder()
                            .phoneNumber(phoneNumber)
                            .name(name)
                            .build();
                    User savedUser = userRepository.save(user);
                    return new UserRegistrationResult(new UserResponse(savedUser), true); // 새로 생성
                });

    }

}
