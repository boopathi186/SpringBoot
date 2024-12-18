package com.example.product.table;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId; // Order reference
    private Long userId;  // User placing the order
    private Long totalPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private List<OrderItem> orderItems;
}
