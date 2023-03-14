package project.avatar.api.repo;

import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.avatar.api.entity.Users;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<Users, String>{
    public List<Users> findByUid(String uid);
}
