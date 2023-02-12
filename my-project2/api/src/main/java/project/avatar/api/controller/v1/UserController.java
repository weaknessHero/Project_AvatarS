package project.avatar.api.controller.v1;

import io.netty.util.internal.UnstableApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;
import project.avatar.api.entity.User;
import project.avatar.api.model.response.CommonResult;
import project.avatar.api.model.response.ListResult;
import project.avatar.api.model.response.SingleResult;
import project.avatar.api.repo.UserJpaRepo;
import project.avatar.api.service.ResponseService;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController //결과 JSON으로 출력
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser(){
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 단일 조회", notes = "userId를 통한 회원 조회")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원ID", required = true) @PathVariable long msrl){
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElse(null));
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                     @ApiParam(value = "회원이름", required = true) @RequestParam String name){
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "userId로 회원정보 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
            @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name){
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long msrl){
        userJpaRepo.deleteById(msrl);
        //성공 결과 정보만 필요한 경우 getSuccessResult()를 이용하여 결과를 출력하면 됨
        return responseService.getSuccessResult();
    }

}
