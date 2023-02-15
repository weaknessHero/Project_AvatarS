package project.avatar.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.avatar.api.entity.Users;
import project.avatar.api.repo.UserJpaRepo;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController //결과 JSON으로 출력
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;

    @ApiOperation(value = "회원 조회", notes = "모든 회원 정보 조회")
    @GetMapping(value = "/user")
    public List<Users> findAllUser(){
        return userJpaRepo.findAll();
    }

    @ApiOperation(value = "회원 입력", notes = "회원 입력")
    @PostMapping(value = "/user")
    public Users save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                      @ApiParam(value = "회원이름", required = true) @RequestParam String name){
        Users users = Users.builder()
                .uid(uid)
                .name(name)
                .build();
        return userJpaRepo.save(users);
    }
}
