package project.avatar.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.avatar.api.entity.Users;
import project.avatar.api.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        Optional<Users> users = userRepository.findByUid(uid);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username: " + uid);
        }

        Users user = users.get();

        return new org.springframework.security.core.userdetails.User(user.getUid(), user.getPassword(), new ArrayList<>());
    }
}
