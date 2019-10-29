package com.example.service.repository;

import com.example.dto.ProductDTO;
import com.example.service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<ProductDTO> findProductById(Long id);
    List<ProductDTO> findProductsBy();
    void deleteById(Long id);
}
