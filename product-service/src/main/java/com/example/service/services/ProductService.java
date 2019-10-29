package com.example.service.services;

import com.example.dto.ProductDTO;
import com.example.service.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductDTO> findOneById(Long id);
    List<ProductDTO> findAll();
    ProductDTO save(Product product);
    void delete(Long id);
}
