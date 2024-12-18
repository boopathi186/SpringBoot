package com.example.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductResponseDto {
    @JsonProperty("productId")
    private Long id;
    @JsonProperty("productName")
    private String product;
    @JsonProperty("productCompany")
    private String company;
    @JsonProperty("productPrice")
    private Long price;
}
