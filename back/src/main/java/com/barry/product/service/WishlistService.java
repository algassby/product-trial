package com.barry.product.service;

import com.barry.product.dto.request.WishlistRequestDTO;
import com.barry.product.dto.response.WishlistResponseDTO;

public interface WishlistService {
    WishlistResponseDTO addProductToWishlist(WishlistRequestDTO requestDTO);
    WishlistResponseDTO removeProductFromWishlist(WishlistRequestDTO requestDTO);
    WishlistResponseDTO getUserWishlist(String userId);
}
