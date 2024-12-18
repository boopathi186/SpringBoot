package com.example.product.Services;

import com.example.product.dto.OrderDto;
import com.example.product.dto.ProductResponseDto;
import com.example.product.feign.ProductClient;
import com.example.product.feign.UserClient;
import com.example.product.repo.OrderRepo;
import com.example.product.table.OrderEntity;
import com.example.product.table.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserClient userClient;
    private final ProductClient productClient;
    private  final OrderRepo orderRepo;
    public  List<ProductResponseDto>  getProducts() {
     return productClient.getProducts();
    }
    public List<Object> getUsers() {
        return userClient.getUser();
    }


    public OrderEntity createOrder(OrderDto orderRequest) {
        // Fetch products from the product service
        List<ProductResponseDto> products = productClient.getProducts();

        // Map OrderItemsDto to OrderItemEntity
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(item -> {
                    // Find the matching product
                    Optional<ProductResponseDto> productOpt = products.stream()
                            .filter(product -> product.getId() != null && product.getId().equals(item.getProductId()))
                            .findFirst();

                    if (productOpt.isEmpty()) {
                        throw new IllegalArgumentException("Product not found for ID: " + item.getProductId());
                    }

                    ProductResponseDto product = productOpt.get();

                    // Create OrderItemEntity
                    OrderItem orderItemEntity = new OrderItem();
                    orderItemEntity.setProductId(product.getId());
                    orderItemEntity.setProductName(product.getProduct());
                    orderItemEntity.setProductCompany(product.getCompany());
                    orderItemEntity.setProductPrice(product.getPrice());
                    orderItemEntity.setQuantity(item.getQuantity());
                    orderItemEntity.setTotalPrice(product.getPrice() * item.getQuantity());

                    return orderItemEntity;
                })
                .collect(Collectors.toList());

        // Calculate total price
        Long totalPrice = orderItems.stream()
                .mapToLong(OrderItem::getTotalPrice)
                .sum();

        // Create OrderEntity
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setUserId(orderRequest.getUserId());
        orderEntity.setOrderItems(orderItems);
        orderEntity.setTotalPrice(totalPrice);

        // Save the order
        return orderRepo.save(orderEntity);
    }

    public List<OrderEntity> getUserOrderById(Long userId) {
        return orderRepo.findByUserId(userId);
    }

}

