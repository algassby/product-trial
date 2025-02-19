package com.barry.product.service.impl;

import com.barry.product.dto.request.WishlistRequestDTO;
import com.barry.product.dto.response.WishlistResponseDTO;
import com.barry.product.exception.NotFoundException;
import com.barry.product.mapper.WishlistMapper;
import com.barry.product.modele.Product;
import com.barry.product.modele.Wishlist;
import com.barry.product.repository.ProductRepository;
import com.barry.product.repository.WishlistRepository;
import com.barry.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;

    @Transactional
    @Override
    public WishlistResponseDTO addProductToWishlist(WishlistRequestDTO requestDTO) {
        Wishlist wishlist = wishlistRepository.findByUserId(requestDTO.getUserId())
                .orElseGet(() -> wishlistRepository.save(new Wishlist(null, requestDTO.getUserId(),
                        new HashSet<>(), LocalDateTime.now(), LocalDateTime.now())));

        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Produit introuvable"));

        wishlist.getProducts().add(product);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return wishlistMapper.wishlistToWishlistResponseDTO(savedWishlist);
    }

    @Transactional
    @Override
    public WishlistResponseDTO removeProductFromWishlist(WishlistRequestDTO requestDTO) {
        Wishlist wishlist = wishlistRepository.findByUserId(requestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Wishlist introuvable"));

        wishlist.getProducts().removeIf(product -> product.getId() == requestDTO.getProductId());
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return wishlistMapper.wishlistToWishlistResponseDTO(savedWishlist);
    }

    @Override
    public WishlistResponseDTO getUserWishlist(String userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Wishlist introuvable"));

        return wishlistMapper.wishlistToWishlistResponseDTO(wishlist);
    }
}
