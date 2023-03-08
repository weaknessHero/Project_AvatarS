package project.avatar.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.avatar.api.entity.User;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUid(String email);
}
