package com.flamexander.springmarket.utils;

import com.flamexander.springmarket.entities.OrderItem;
import com.flamexander.springmarket.entities.Product;
import com.flamexander.springmarket.services.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {
    private ProductService productService;

    @Autowired
    public void setProductsService(ProductService productsService) {
        this.productService = productsService;
    }

    private List<OrderItem> items;
    private Double totalCost;

    public ShoppingCart() {
        items = new ArrayList<>();
        totalCost = 0.0;
    }

    public void add(Long productId) {
        Product product = productService.findById(productId);
        this.add(product);
    }

    public void add(Product product) {
        OrderItem orderItem = findOrderItemFromProduct(product);
        if (orderItem == null) {
            orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setItemPrice(product.getPrice());
            orderItem.setQuantity(0L);
            orderItem.setId(0L);
            orderItem.setTotalPrice(0.0);
            items.add(orderItem);
        }
        orderItem.setQuantity(orderItem.getQuantity() + 1);
        recalculate();
    }

    public void setQuantity(Long productId, Long quantity) {
        Product product = productService.findById(productId);
        this.setQuantity(product, quantity);
    }

    public void setQuantity(Product product, Long quantity) {
        OrderItem orderItem = findOrderItemFromProduct(product);
        if (orderItem == null) {
            return;
        }
        orderItem.setQuantity(quantity);
        recalculate();
    }

    public void remove(Long productId) {
        Product product = productService.findById(productId);
        this.remove(product);
    }

    public void remove(Product product) {
        OrderItem orderItem = findOrderItemFromProduct(product);
        if (orderItem == null) {
            return;
        }
        items.remove(orderItem);
        recalculate();
    }

    private void recalculate() {
        totalCost = 0.0;
        for (OrderItem o : items) {
            o.setTotalPrice(o.getQuantity() * o.getProduct().getPrice());
            totalCost += o.getTotalPrice();
        }
    }

    private OrderItem findOrderItemFromProduct(Product product) {
        return items.stream().filter(o -> o.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
    }
}
