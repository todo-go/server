package kwonyonghoon.todogo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationResult {
    private UserResponse userResponse;
    private boolean isNewUser;
}
