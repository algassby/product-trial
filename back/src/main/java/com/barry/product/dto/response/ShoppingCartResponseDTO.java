package com.barry.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ShoppingCartResponseDTO {
    private String userId;
    private List<CartItemResponseDTO> items;
    private double totalAmount;
}