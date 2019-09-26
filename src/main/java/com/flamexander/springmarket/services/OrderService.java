package com.flamexander.springmarket.services;


import com.flamexander.springmarket.entities.Order;
import com.flamexander.springmarket.entities.OrderItem;
import com.flamexander.springmarket.entities.OrderStatus;
import com.flamexander.springmarket.entities.User;
import com.flamexander.springmarket.repositories.OrderRepository;
import com.flamexander.springmarket.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order makeOrder(ShoppingCart cart, User user) {
        Order order = new Order();
        order.setId(0L);
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setPrice(cart.getTotalCost());
        order.setOrderItems(new ArrayList<>(cart.getItems()));
        for (OrderItem o : order.getOrderItems()) {
            o.setOrder(order);
        }
        return order;
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order changeOrderStatus(Order order, OrderStatus newStatus) {
        order.setStatus(newStatus);
        return saveOrder(order);
    }
}
