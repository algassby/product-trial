package com.barry.product.mapper;

import com.barry.product.dto.request.ProductRequest;
import com.barry.product.dto.response.ProductResponse;
import com.barry.product.modele.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest productRequest);
    ProductResponse toResponse(Product product);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromRequest(ProductRequest productRequest, @MappingTarget Product product);
}
