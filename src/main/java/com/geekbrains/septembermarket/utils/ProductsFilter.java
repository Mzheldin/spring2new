package com.geekbrains.septembermarket.utils;

import com.geekbrains.septembermarket.entities.Product;
import com.geekbrains.septembermarket.repositories.specifications.ProductSpecifications;
import com.geekbrains.septembermarket.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class ProductsFilter {
    private Specification<Product> specification;
    private StringBuilder filtersString;
    private CategoryService categoryService;

    public Specification<Product> getSpecification() {
        return specification;
    }

    public StringBuilder getFiltersString() {
        return filtersString;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public StringBuilder getFilterStringBuilder(HttpServletRequest request){
        filtersString = new StringBuilder();
        specification = Specification.where(null);

        if (request.getParameter("word") != null && !request.getParameter("word").isEmpty()) {
            specification = specification.and(ProductSpecifications.titleContains(request.getParameter("word")));
            filtersString.append("&word=").append(request.getParameter("word"));
        }

        if (request.getParameter("min") != null && !request.getParameter("min").isEmpty()) {
            specification = specification.and(ProductSpecifications.priceGreaterThanOrEq(new BigDecimal(request.getParameter("min"))));
            filtersString.append("&min=").append(request.getParameter("min"));
        }

        if (request.getParameter("max") != null && !request.getParameter("max").isEmpty()) {
            specification = specification.and(ProductSpecifications.priceLesserThanOrEq(new BigDecimal(request.getParameter("max"))));
            filtersString.append("&max=").append(request.getParameter("max"));
        }

        if (request.getParameter("category") != null && !request.getParameter("category").isEmpty()){
            specification = specification.and(ProductSpecifications.categoryEquals(categoryService.findOneByTitle(request.getParameter("category"))));
        }

        return filtersString;
    }
}
