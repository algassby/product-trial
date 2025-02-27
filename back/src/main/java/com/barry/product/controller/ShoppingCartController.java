package com.barry.product.controller;

import com.barry.product.annotations.ApiRestController;
import com.barry.product.annotations.CheckCurrentUserIdentification;
import com.barry.product.dto.request.CartItemRequestDTO;
import com.barry.product.dto.response.ShoppingCartResponseDTO;
import com.barry.product.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@ApiRestController("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @CheckCurrentUserIdentification
    @GetMapping("/{userId}")
    public ShoppingCartResponseDTO getCart(@PathVariable String userId) {
        return shoppingCartService.getCartByUser(userId);
    }

    @CheckCurrentUserIdentification
    @PostMapping("/{userId}/add")
    public ShoppingCartResponseDTO addProductToCart(@PathVariable String userId, @RequestBody CartItemRequestDTO request) {
        return shoppingCartService.addProductToCart(userId, request);
    }
    @CheckCurrentUserIdentification
    @DeleteMapping("/{userId}/remove/{productId}")
    public ShoppingCartResponseDTO removeProductFromCart(@PathVariable String userId, @PathVariable Long productId) {
        return shoppingCartService.removeProductFromCart(userId, productId);
    }

    @CheckCurrentUserIdentification
    @DeleteMapping("/{userId}/clear")
    public ShoppingCartResponseDTO clearCart(@PathVariable String userId) {
        return shoppingCartService.clearCart(userId);
    }
}