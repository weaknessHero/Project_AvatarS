package project.avatar.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.avatar.api.entity.Products;

import java.util.List;

@Repository
public interface ProductsRepository extends MongoRepository<Products, String> {
    List<Products> findByTagsContaining(String tag);
}
