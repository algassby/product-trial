package com.barry.product.service;


import com.barry.product.dto.request.ProductRequest;
import com.barry.product.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(int id);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(int id, ProductRequest productRequest);
    ProductResponse partialUpdateProduct(int id, ProductRequest productRequest);
    void deleteProduct(int id);
}