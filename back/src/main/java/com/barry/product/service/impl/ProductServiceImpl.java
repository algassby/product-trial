package com.barry.product.service.impl;

import com.barry.product.dto.request.ProductRequest;
import com.barry.product.dto.response.ProductResponse;
import com.barry.product.exception.NotFoundException;
import com.barry.product.mapper.ProductMapper;
import com.barry.product.modele.Product;
import com.barry.product.repository.ProductRepository;
import com.barry.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND_WITH_ID = "Produit non trouvé avec l'ID";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(int id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_WITH_ID + " : " + id));
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(int id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_WITH_ID + " : " + id));

        productMapper.updateProductFromRequest(productRequest, product);
        product.setUpdatedAt(Instant.now());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse partialUpdateProduct(int id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_WITH_ID + " : " + id));

        productMapper.updateProductFromRequest(productRequest, product);
        product.setUpdatedAt(Instant.now());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(PRODUCT_NOT_FOUND_WITH_ID + " : " + id);
        }
        productRepository.deleteById(id);
    }
}
