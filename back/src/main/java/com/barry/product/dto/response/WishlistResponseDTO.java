package com.barry.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponseDTO {
    private Long id;
    private String userId;
    private Set<ProductResponse> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
