package kwonyonghoon.todogo.user;

import jakarta.transaction.Transactional;
import kwonyonghoon.todogo.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse registerUser(String phoneNumber, String name){
         User user = User.builder()
                 .phoneNumber(phoneNumber)
                 .name(name)
                 .build();

         User savedUser = userRepository.save(user);
         return new UserResponse(savedUser);
    }

}
