package com.barry.product.service.impl;

import com.barry.product.annotations.CheckProductItem;
import com.barry.product.dto.request.CartItemRequestDTO;
import com.barry.product.dto.response.ShoppingCartResponseDTO;
import com.barry.product.exception.NotFoundException;
import com.barry.product.mapper.ShoppingCartMapper;
import com.barry.product.modele.CartItem;
import com.barry.product.modele.Product;
import com.barry.product.modele.ShoppingCart;
import com.barry.product.repository.CartItemRepository;
import com.barry.product.repository.ProductRepository;
import com.barry.product.repository.ShoppingCartRepository;
import com.barry.product.service.ShoppingCartService;
import com.barry.product.utils.predicate.CheckEligibilityToAddingProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private  final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CheckEligibilityToAddingProduct checkEligibilityToAddingProduct;

    @Override
    public ShoppingCartResponseDTO getCartByUser(String userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> shoppingCartRepository.save(
                        ShoppingCart.builder().userId(userId).build()
                ));

        return shoppingCartMapper.shoppingCartToShoppingCartResponseDTO(cart);
    }


    @Override
    @CheckProductItem
    public ShoppingCartResponseDTO addProductToCart(String userId, CartItemRequestDTO request) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> shoppingCartRepository.save(ShoppingCart.builder().userId(userId).build()));

        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty()) {
            throw new NotFoundException("Produit non trouvé.");
        }

        Product product = productOpt.get();

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == request.getProductId())
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cart.getItems().add(newItem);
        }

        shoppingCartRepository.save(cart);

        if(checkEligibilityToAddingProduct.test(product, request.getQuantity())){
            product.setQuantity(product.getQuantity() - request.getQuantity());
            productRepository.save(product);
        }


        return shoppingCartMapper.shoppingCartToShoppingCartResponseDTO(cart);
    }

    @Override
    public ShoppingCartResponseDTO removeProductFromCart(String userId, Long productId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Panier introuvable"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Produit non trouvé dans le panier"));

        Product product = cartItem.getProduct();

        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
        productRepository.save(product);

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return shoppingCartMapper.shoppingCartToShoppingCartResponseDTO(cart);
    }

    @Override
    public ShoppingCartResponseDTO clearCart(String userId) {

        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException("Panier introuvable.")
        );

        cart.getItems().forEach(cartItem -> {
            var product = cartItem.getProduct();

            int totalQuantity = cart.getItems().stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            productRepository.findById(product.getId()).ifPresent(productUpdated -> {
                productUpdated.setQuantity(productUpdated.getQuantity() + totalQuantity);
                productRepository.save(productUpdated);
            });
        });

        cart.getItems().clear();
        shoppingCartRepository.save(cart);

        return shoppingCartMapper.shoppingCartToShoppingCartResponseDTO(cart);
    }
}