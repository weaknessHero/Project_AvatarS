package project.avatar.api.controller.products;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.avatar.api.entity.Products;
import project.avatar.api.model.response.ListResult;
import project.avatar.api.repo.ProductsRepository;
import project.avatar.api.service.ResponseService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Api(tags = {"3. Product"})
@RestController
@RequestMapping(value = "/products", consumes = "application/json")
public class ProductController {

    @Autowired
    private ProductsRepository productsRepository;
    private ResponseService responseService;

    @Value("${image.storage.path}")
    private String imageStoragePath;

    @ApiOperation(value = "상품 등록", notes = "상품을 등록한다")
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Products products){
        if(products.getPrice()<=0){
            return ResponseEntity.badRequest().body("가격은 0원보다 높게 설정해주세요");
        }else {
            Path imageUrl = Paths.get(imageStoragePath, UUID.randomUUID().toString());
            products.setImageUrl(imageUrl);

            Products savedProduct = productsRepository.save(products);
            return ResponseEntity.ok(savedProduct);
        }
    }

    @ApiOperation(value="상품 리스트 조회", notes = "모든 상품을 조회한다")
    @GetMapping(value = "/selectAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ListResult<Products> selectAllProducts(){
        //return (ListResult<Products>) productsRepository.findAll();
        return responseService.getListResult(productsRepository.findAll());
    }

    @ApiOperation(value="개별 상품 조회", notes = "단일 상품을 조회한다")
    @GetMapping("/select/{name}")
    private Products findById(@PathVariable String id){
        return productsRepository.findById(id).get();
    }

    @ApiOperation(value="개별 상품 업데이트", notes = "상품 정보를 업데이트한다")
    @PutMapping("/update/{name}")
    private Products updateById(@PathVariable String id){
        return productsRepository.findById(id).get();
    }

    @ApiOperation(value="상품 삭제", notes = "상품 삭제를 삭제한다")
    @DeleteMapping("/delete/{name}")
    private Products deleteById(@PathVariable String id){
        return productsRepository.findById(id).get();
    }
}
