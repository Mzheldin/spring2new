package com.flamexander.springmarket.services;

import com.flamexander.springmarket.entities.Product;
import com.flamexander.springmarket.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductsRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize, Specification<Product> productSpecification) {
        return productRepository.findAll(productSpecification, PageRequest.of(pageNumber, pageSize));
    }

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public boolean isProductWithTitleExists(String title) {
        return productRepository.findOneByTitle(title) != null;
    }

    public Product findByTitle(String title) {
        return productRepository.findOneByTitle(title);
    }
}
