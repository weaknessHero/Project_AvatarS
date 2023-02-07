package project.avatar.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.avatar.api.entity.Users;

public interface UserJpaRepo extends JpaRepository<Users, Long> {
}
