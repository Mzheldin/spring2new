package com.example.service.services;

import com.example.dto.ProductDTO;
import com.example.service.entity.Product;
import com.example.service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ProductDTO> findOneById(Long id) {
        return repository.findProductById(id);
    }

    @Override
    public List<ProductDTO> findAll() {
        return repository.findProductsBy();
    }

    @Override
    public ProductDTO save(Product product) {
        return (ProductDTO) repository.save(product);
    }

    @Override
    public void delete(Long id) {
        if (repository.findProductById(id).isPresent())
            repository.deleteById(id);
    }
}
