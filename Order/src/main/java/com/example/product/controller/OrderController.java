package com.example.product.controller;

import com.example.product.Services.OrderService;
import com.example.product.dto.OrderDto;
import com.example.product.dto.ProductResponseDto;
import com.example.product.table.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public  List<ProductResponseDto>  getProducts()
    {
        return orderService.getProducts();
    }

    @GetMapping("/user")
    public List<Object> getUsers()
    {
        return orderService.getUsers();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderEntity>> getUserOrderById(@PathVariable Long userId) {
        List<OrderEntity> userOrders = orderService.getUserOrderById(userId);
        if (userOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userOrders);
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderDto orderRequest) {
        OrderEntity createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }
}
