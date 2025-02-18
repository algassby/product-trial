package com.barry.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItemResponseDTO {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private double totalPrice;
}