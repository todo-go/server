package kwonyonghoon.todogo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddUserRequest {

    private String phoneNumber;
    private String name;

}
