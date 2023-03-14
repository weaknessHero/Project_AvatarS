package project.avatar.api.service.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import project.avatar.api.advice.Exception.CUserNotFoundException;
import project.avatar.api.repo.UserRepository;
//import project.avatar.api.repo.UserJpaRepo;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String userPk) {
        return userRepository.findByd(Math.toIntExact(Long.valueOf(userPk))).orElseThrow(CUserNotFoundException::new);
    }
}
