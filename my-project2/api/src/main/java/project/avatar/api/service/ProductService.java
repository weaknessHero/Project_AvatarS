package project.avatar.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.avatar.api.dto.ProductDTO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
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
