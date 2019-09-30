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

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {
    private ProductService productService;

    @Autowired
    public void setProductsService(ProductService productsService) {
        this.productService = productsService;
    }

    private Map<Long, OrderItem> items;
    private BigDecimal totalCost;

    @PostConstruct
    public void init(){
        items = new HashMap<>();
        totalCost = BigDecimal.valueOf(0.0);
    }

    public ShoppingCart() {
        items = new HashMap<>();
        totalCost = BigDecimal.valueOf(0.0);
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
            orderItem.setTotalPrice(BigDecimal.valueOf(0.0));
            items.put(product.getId(), orderItem);
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
        if (orderItem == null)
            return;
        items.remove(product.getId());
        recalculate();
    }

    private void recalculate() {
        totalCost = BigDecimal.valueOf(0.0);
        for (OrderItem o : items.values()) {
            o.setTotalPrice(o.getProduct().getPrice().multiply(new BigDecimal(o.getQuantity())));
            totalCost = totalCost.add(o.getTotalPrice());
        }
    }

    private OrderItem findOrderItemFromProduct(Product product) {
        return items.get(product.getId());
        //return items.stream().filter(o -> o.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
    }
}
