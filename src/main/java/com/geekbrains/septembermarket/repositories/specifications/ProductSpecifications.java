package com.geekbrains.septembermarket.repositories.specifications;

import com.geekbrains.septembermarket.entities.Category;
import com.geekbrains.septembermarket.entities.Product;
import com.geekbrains.septembermarket.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;


public class ProductSpecifications {

    public static Specification<Product> titleContains(String word) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Product> priceGreaterThanOrEq(BigDecimal value) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
        };
    }

    public static Specification<Product> priceLesserThanOrEq(BigDecimal value) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
        };
    }

    public static Specification<Product> categoryEquals(Category category) {
        if (category != null)
            return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
        return null;
    }
}
