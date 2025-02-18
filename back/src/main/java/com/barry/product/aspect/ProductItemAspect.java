package com.barry.product.aspect;

import com.barry.product.dto.request.CartItemRequestDTO;
import com.barry.product.exception.NotFoundException;
import com.barry.product.repository.ProductRepository;
import com.barry.product.utils.predicate.CheckEligibilityToAddingProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.barry.product.service.impl.ProductServiceImpl.PRODUCT_NOT_FOUND_WITH_ID;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductItemAspect {

    private final CheckEligibilityToAddingProduct checkEligibilityToAddingProduct;
    private final ProductRepository productRepository;

    @Pointcut("@annotation(com.barry.product.annotations.CheckProductItem)")
    public void checkItemProduct() {}

    @Before("checkItemProduct()")
    public void checkIsEmailAdmin(JoinPoint joinPoint){

        var carRequest = (CartItemRequestDTO) joinPoint.getArgs()[1];

        var product =  productRepository.findById(carRequest.getProductId())

                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_WITH_ID + " : " + carRequest.getProductId()));

        if (!checkEligibilityToAddingProduct.test(product, carRequest.getQuantity())) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Produit indisponible.");
        }
    }
}
