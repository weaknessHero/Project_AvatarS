package project.avatar.api.service.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.avatar.api.advice.Exception.CUserNotFoundException;
import project.avatar.api.entity.Users;
import project.avatar.api.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
//import project.avatar.api.repo.UserJpaRepo;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }*/
   /* @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return UserRepository.findByUId(Math.toIntExact(Long.valueOf(userPk))).orElseThrow(CUserNotFoundException::new);
    }*/

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException{
        Users user = (Users) userRepository.findByUid(uid);
        if (user == null){
            throw new UsernameNotFoundException(uid+"의 유저가 존재하지 않습니다.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUid(), user.getPassword(), new ArrayList<>()
        );
    }
}
