package com.example.client.controller;

import com.example.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductClientImpl {

    private ProductClient productClient;

    @Autowired
    public void setProductClient(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/get-products")
    public String getProducts(Model model){
        model.addAttribute("products", productClient.getProducts());
        return "products-view";
    }
}
