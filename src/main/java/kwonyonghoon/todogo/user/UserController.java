package kwonyonghoon.todogo.user;

import kwonyonghoon.todogo.dto.AddUserRequest;
import kwonyonghoon.todogo.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> registerUser(@RequestBody AddUserRequest request){
        UserResponse response = userService.registerUser(request.getPhoneNumber(), request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
