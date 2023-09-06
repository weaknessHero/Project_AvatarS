package project.avatar.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.avatar.api.entity.Closet;

@Repository
public interface ClothesRepository extends MongoRepository<Closet, String> {
}
