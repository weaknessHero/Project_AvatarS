package project.avatar.api.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.avatar.api.entity.User;
import project.avatar.api.repo.UserJpaRepo;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    @GetMapping(value = "/user")
    public List<User> findAllUser(){
        return userJpaRepo.findAll();
    }
    @PostMapping(value = "/user")
    public User save(){
        User user = User.builder()
                .uid("saedongh5@naver.com")
                .name("세동")
                .build();
        return userJpaRepo.save(user);
    }
}
