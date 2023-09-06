package project.avatar.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.avatar.api.entity.Clothes;

@Repository
public interface ClothesRepository extends MongoRepository<Clothes, String> {
}
