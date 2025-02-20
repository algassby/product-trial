package com.barry.product.controller;

import com.barry.product.annotations.ApiRestController;
import com.barry.product.annotations.CheckCurrentUserIdentification;
import com.barry.product.dto.request.WishlistRequestDTO;
import com.barry.product.dto.response.WishlistResponseDTO;
import com.barry.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiRestController("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add")
    @CheckCurrentUserIdentification
    public ResponseEntity<WishlistResponseDTO> addProduct(@RequestBody WishlistRequestDTO requestDTO) {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(requestDTO));
    }

    @DeleteMapping("/remove")
    @CheckCurrentUserIdentification
    public ResponseEntity<WishlistResponseDTO> removeProduct(@RequestBody WishlistRequestDTO requestDTO) {
        return ResponseEntity.ok(wishlistService.removeProductFromWishlist(requestDTO));
    }

    @GetMapping("/{userId}")
    @CheckCurrentUserIdentification
    public ResponseEntity<WishlistResponseDTO> getWishlist(@PathVariable String userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }
}
