package project.avatar.api.controller.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.avatar.api.entity.Users;
import project.avatar.api.repo.UserRepository;
import project.avatar.api.config.security.*;

import java.util.List;
import java.util.Optional;
//import project.avatar.api.entity.Users;

//import project.avatar.api.repo.UserJpaRepo;


@Api(tags = {"1. Sign"})
//@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users", consumes = "application/json")
public class SignController {

    @Autowired
    UserRepository userRepository;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Users users){
        if (userRepository.existsById(users.getUid(users))){
            return ResponseEntity.badRequest().body("Id가 이미 사용중입니다.");
        }
        PasswordEncoder encoder = passwordEncoder;
        if (encoder != null) {
            String password = users.getPassword() != null ? String.valueOf(users.getPassword()) : "";
            String encodedPassword = passwordEncoder.encode(password);
            users.setPassword(encodedPassword);
        }
        return ResponseEntity.ok(userRepository.save(users));
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@RequestBody Users users){
        List<Users> user = userRepository.findByUid(users.getUid());

        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        if(!users.getPassword().equals(users.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        return ResponseEntity.ok(true);
        //String jwt = generateJwtToken(user);

        //return ResponseEntity.ok(new JwtResponse(jwt));
    }

    /*@ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")



    /*@PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/{users}")
    public User getUser(@PathVariable String uid){
        return (User) userRepository.findByUserid(uid);
    }*/

    /*private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;*/

    /*@ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {

        Users users = userJpaRepo.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, users.getPassword()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(users.getMsrl()), users.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name) {

        userJpaRepo.save(Users.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }*/
}
