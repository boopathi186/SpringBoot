package com.example.product.feign;

import com.example.product.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service",url = "${external.api.product-url}")
public interface ProductClient {

    @GetMapping("/product")
    List<ProductResponseDto>  getProducts();
}
