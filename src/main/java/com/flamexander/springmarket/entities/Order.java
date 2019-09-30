package com.flamexander.springmarket.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private User user;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(name = "status")
    @Enumerated
    private OrderStatus status;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress deliveryAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @CreationTimestamp
    private LocalDateTime updateAt;

    public Order(User user, OrderStatus status, BigDecimal deliveryPrice, DeliveryAddress deliveryAddress, String phoneNumber, LocalDateTime deliveryDate) {
        this.user = user;
        this.orderItems = new ArrayList<>();
        this.status = status;
        this.price = new BigDecimal(0);
        this.deliveryPrice = deliveryPrice;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
    }

    public void addItem(OrderItem item){
        orderItems.add(item);
        item.setOrder(this);
        price = price.add(item.getTotalPrice());
    }
}