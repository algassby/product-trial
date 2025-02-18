package com.barry.product.utils.predicate;

import com.barry.product.modele.Product;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class CheckProductQuantityPredicate implements Predicate<Product> {

    @Override
    public boolean test(Product product) {
        return product.getQuantity() > 0;
    }
}
