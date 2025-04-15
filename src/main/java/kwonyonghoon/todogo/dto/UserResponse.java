package kwonyonghoon.todogo.dto;

import kwonyonghoon.todogo.user.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String phoneNumber;
    private final String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.name = user.getName();
    }

}
