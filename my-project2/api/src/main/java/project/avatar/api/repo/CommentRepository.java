package project.avatar.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.avatar.api.entity.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
