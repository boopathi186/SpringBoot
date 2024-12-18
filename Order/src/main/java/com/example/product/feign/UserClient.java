package com.example.product.feign;

import com.example.product.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service",url = "${external.api.user-url}")
public interface UserClient {
    @GetMapping("/user")
    List<Object> getUser();
}
