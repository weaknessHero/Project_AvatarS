package project.avatar.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.avatar.api.entity.User;

public interface UserJpaRepo extends JpaRepository<User, Long> {
}
