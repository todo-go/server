package kwonyonghoon.todogo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import kwonyonghoon.todogo.user.User;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserResponse {

    private final Long id;
    private final String uuid;
    private final String phoneNumber;
    private final String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.uuid = user.getUuid();
        this.phoneNumber = user.getPhoneNumber();
        this.name = user.getName();
    }
}
