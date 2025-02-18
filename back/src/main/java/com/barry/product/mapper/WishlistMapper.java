package com.barry.product.mapper;

import com.barry.product.dto.response.WishlistResponseDTO;
import com.barry.product.modele.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mapping(source = "products", target = "products")
    WishlistResponseDTO wishlistToWishlistResponseDTO(Wishlist wishlist);

    List<WishlistResponseDTO> wishlistToWishlistResponseDTOs(List<Wishlist> wishlists);
}
