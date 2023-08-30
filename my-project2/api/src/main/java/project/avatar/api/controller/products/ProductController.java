package project.avatar.api.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.avatar.api.dto.ProductDTO;
import project.avatar.api.entity.Products;
import project.avatar.api.model.response.ListResult;
import project.avatar.api.service.ProductService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/")
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
        Products createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable String id) {
        Products product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable String id, @RequestBody Products product) {
        Products existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            product.setId(id); // Set the ID of the updated product
            productService.updateProduct(product);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        Products existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<ListResult<ProductDTO>> searchProductsByTag(@RequestParam String tag) {
        List<ProductDTO> products = productService.searchProductsByTags(tag);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ListResult<ProductDTO> result = new ListResult<>(true, "Success", products);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

