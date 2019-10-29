package com.example.service.controller;

import com.example.dto.ProductDTO;
import com.example.service.entity.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductController {

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDTO> getProducts();

    @GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDTO getProduct(@PathVariable Long productId);

    @DeleteMapping(value = "/products/{productId}")
    int deleteProduct(@PathVariable Long productId);

    @PostMapping("/products")
    ProductDTO addProduct(@RequestBody Product product);

    @PutMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    ProductDTO updateProduct(@RequestBody Product product);
}
