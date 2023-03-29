package project.avatar.api.controller.products;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.avatar.api.entity.Products;
import project.avatar.api.repo.ProductsRepository;

@Api(tags = {"3. Product"})
@RestController
@RequestMapping(value = "/products", consumes = "application/json")
public class ProductController {

    //@Autowired
    //private ProductsService productsService;
    @Autowired
    private ProductsRepository productsRepository;
    //@Autowired
    //private ProductsRepository productsRepository;
    //@Autowired private ProductsRepository productsRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Products products){
        //ProductsService productsService = new ProductsService();
        //productsService.save(products);
        if(products.getPrice()<=0){
            return ResponseEntity.badRequest().body("가격은 0원보다 높게 설정해주세요");
        }else {
            return ResponseEntity.ok(productsRepository.save(products));
        }
    }

    /*@Service
    public class ProductsService{
        @Autowired
        private ProductsRepository productsRepository;

        public void save(Products products){
            validateProducts(products);
            if(productsRepository != null){
                productsRepository.save(products);
            }
        }

        private void validateProducts(Products products){
            if(products.getPrice() < 0){
                throw new IllegalArgumentException("가격은 0원이 넘어야합니다.");
            }
        }
    }*/
}
