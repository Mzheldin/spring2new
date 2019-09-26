package com.flamexander.springmarket.controllers;

import com.flamexander.springmarket.entities.Product;
import com.flamexander.springmarket.services.CategoryService;
import com.flamexander.springmarket.services.ProductService;
import com.flamexander.springmarket.utils.ImageSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/edit/{id}")
    public String addProductPage(Model model, @PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            product = new Product();
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("product", product);
        return "edit-product";
    }

    // todo много вопросов как при создании, так и при апдейте
    @PostMapping("/edit")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("product", product);

        if (bindingResult.hasErrors()) {
            return "edit-product";
        }
        Product existing = productService.findByTitle(product.getTitle());
        if (existing != null && (product.getId() == null || !product.getId().equals(existing.getId()))) {
            model.addAttribute("productCreationError", "Product title already exists");
            return "edit-product";
        }

        productService.saveOrUpdate(product);
        return "redirect:/";
    }
}
