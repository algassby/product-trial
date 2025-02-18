package com.barry.product.utils.predicate;

import com.barry.product.modele.Product;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;

@Component
public class CheckEligibilityToAddingProduct implements BiPredicate<Product, Integer> {


    @Override
    public boolean test(Product product, Integer integer) {
        return product.getQuantity() - integer > 0 ;
    }
}
