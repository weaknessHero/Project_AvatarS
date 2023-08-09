package project.avatar.api.controller.products;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.avatar.api.dto.ProductDTO;
import project.avatar.api.entity.Products;
import project.avatar.api.model.response.ListResult;
import project.avatar.api.repo.ProductsRepository;
import project.avatar.api.service.ProductService;
import project.avatar.api.service.ResponseService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam("query") String query) {
        List<ProductDTO> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }
}

