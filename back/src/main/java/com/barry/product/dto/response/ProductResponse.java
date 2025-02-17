package com.barry.product.dto.response;

import com.barry.product.modele.InventoryStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class ProductResponse {
    private int id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private int shellId;
    private InventoryStatus inventoryStatus;
    private double rating;
    private Instant createdAt;
    private Instant updatedAt;
}
