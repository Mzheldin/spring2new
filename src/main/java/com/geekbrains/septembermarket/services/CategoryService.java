package com.geekbrains.septembermarket.services;

import com.geekbrains.septembermarket.entities.Category;
import com.geekbrains.septembermarket.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findOneByTitle(String title){
        return categoryRepository.findOneByTitleEquals(title);
    }

    public Optional<Category> findOneById(Long id){
        return categoryRepository.findById(id);
    }
}
