package project.avatar.api.controller.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String uid;
    private String password;
    private String name;
}
