package com.geekbrains.septembermarket.services;

import com.geekbrains.septembermarket.entities.Order;
import com.geekbrains.septembermarket.entities.OrderHistory;
import com.geekbrains.septembermarket.entities.User;
import com.geekbrains.septembermarket.repositories.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderHistoryService {

    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    public void setOrderHistoryRepository(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    public List<OrderHistory> getAllHistory(){
        return orderHistoryRepository.findAll();
    }

    public List<OrderHistory> getHistoryForUser(User user){
        List<OrderHistory> orderHistories = new ArrayList<>();
        List<Order> userOrders = user.getOrders();
        for (Order order : userOrders)
            orderHistories.addAll(orderHistoryRepository.findAllByOrder(order));
        return orderHistories;
    }
}
