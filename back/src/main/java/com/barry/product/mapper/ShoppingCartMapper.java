package com.barry.product.mapper;

import com.barry.product.dto.response.CartItemResponseDTO;
import com.barry.product.dto.response.ShoppingCartResponseDTO;
import com.barry.product.modele.CartItem;
import com.barry.product.modele.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {
    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    @Mapping(expression = "java(item.getProduct().getPrice() * item.getQuantity())", target = "totalPrice")
    CartItemResponseDTO cartItemToCartItemResponseDTO(CartItem item);

    List<CartItemResponseDTO> cartItemsToCartItemResponseDTOs(List<CartItem> items);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "items", target = "items")
    @Mapping(expression = "java(cart.getItems().stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum())", target = "totalAmount")
    ShoppingCartResponseDTO shoppingCartToShoppingCartResponseDTO(ShoppingCart cart);
}