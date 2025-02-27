package com.barry.product.controller;

import com.barry.product.annotations.AdminOnly;
import com.barry.product.annotations.ApiRestController;
import com.barry.product.annotations.CheckEmail;
import com.barry.product.dto.request.ProductRequest;
import com.barry.product.dto.response.ProductResponse;
import com.barry.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiRestController("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @CheckEmail
    @AdminOnly
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PutMapping("/{id}")
    @CheckEmail
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @PatchMapping("/{id}")
    @AdminOnly
    public ResponseEntity<ProductResponse> partialUpdateProduct(@PathVariable int id, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.partialUpdateProduct(id, productRequest));
    }

    @DeleteMapping("/{id}")
    @CheckEmail
    @AdminOnly
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
