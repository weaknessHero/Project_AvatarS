package project.avatar.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import project.avatar.api.entity.Posts;

@Repository
public interface PostRepository extends MongoRepository<Posts, String>{
    List<Posts> findByUsername(String username);
}
