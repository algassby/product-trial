package com.barry.product.service;

import com.barry.product.dto.request.CartItemRequestDTO;
import com.barry.product.dto.response.ShoppingCartResponseDTO;

public interface ShoppingCartService {
    ShoppingCartResponseDTO getCartByUser(String userId);
    ShoppingCartResponseDTO addProductToCart(String userId, CartItemRequestDTO request);
    ShoppingCartResponseDTO removeProductFromCart(String userId, Long productId);
    ShoppingCartResponseDTO clearCart(String userId);
}
