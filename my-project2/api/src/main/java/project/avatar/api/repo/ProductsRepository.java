package project.avatar.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.avatar.api.entity.Products;

import java.util.List;

public interface ProductsRepository extends MongoRepository<Products, String> {
    public List<Products> findByName(String name);
}
