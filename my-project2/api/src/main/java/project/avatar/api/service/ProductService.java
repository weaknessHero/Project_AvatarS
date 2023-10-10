package project.avatar.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.vision.v1.Product;

import project.avatar.api.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

import project.avatar.api.entity.Products;
import project.avatar.api.repo.ProductsRepository;


@Service
public class ProductService {


    @Autowired
    private ProductsRepository productsRepository;

    public Products createProduct(Products products) {
        return productsRepository.save(products);
    }

    public Products getProductById(String id) {
        return productsRepository.findById(id).orElse(null);
    }

    public void updateProduct(Products products) {
        productsRepository.save(products);
    }

    public void deleteProduct(String id) {
        productsRepository.deleteById(id);
    }

    public List<ProductDTO> searchProductsByTags(String tag) {
        List<ProductDTO> result = new ArrayList<>();

        List<Products> products = productsRepository.findByTagsContaining(tag);
        for (Products product : products) {
            ProductDTO productDTO = convertToProductDTO(product);
            result.add(productDTO);
        }

        return result;
    }

    public List<ProductDTO> getAllProducts(){
        List<Products> products = productsRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        
        for (Products product : products) {
            ProductDTO productDto = convertToProductDTO(product);
            productDTOs.add(productDto);
        }
        
        return productDTOs;
    }
    

    private ProductDTO convertToProductDTO(Products product) {
        ProductDTO productDto = new ProductDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getCategory(),
                product.getPrice(),
                product.getImageUrl(),
                product.getBuyUrl(),
                product.getTags()
        );

        return productDto;
    }



    /* SQLite
    @Autowired
    private ProductsRepository productsRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Products createProduct(Products products){
        return productsRepository.save(products);
    }

    @Transactional
    public Products getProductById(Long id){
        return productsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateProduct(Products products){
        productsRepository.save(products);
    }

    @Transactional
    public void deleteProduct(Long id){
        productsRepository.deleteById(id);
    }*/
}
