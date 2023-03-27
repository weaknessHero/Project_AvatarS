package project.avatar.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.avatar.api.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users, String>{

    public List<Users> findByUid(String uid);
    //public List<Users> findById(String _id);
}
