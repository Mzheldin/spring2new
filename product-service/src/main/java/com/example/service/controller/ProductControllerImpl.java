package com.example.service.controller;

import com.example.dto.ProductDTO;
import com.example.service.entity.Product;
import com.example.service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductControllerImpl implements ProductController {

    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getProducts(){
        return service.findAll();
    }

    @GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO getProduct(@PathVariable Long productId){
        if (service.findOneById(productId).isPresent())
            return service.findOneById(productId).get();
        else return null;
    }

    @DeleteMapping(value = "/products/{productId}")
    public int deleteProduct(@PathVariable Long productId){
        service.delete(productId);
        return HttpStatus.OK.value();
    }

    @PostMapping("/products")
    public ProductDTO addProduct(@RequestBody Product product){
        product.setId(0L);
        return service.save(product);
    }

    @PutMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO updateProduct(@RequestBody Product product){
        return service.save(product);
    }
}
