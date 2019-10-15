package com.geekbrains.septembermarket.repositories;

import com.geekbrains.septembermarket.entities.Order;
import com.geekbrains.septembermarket.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findAllByOrder(Order order);
}
