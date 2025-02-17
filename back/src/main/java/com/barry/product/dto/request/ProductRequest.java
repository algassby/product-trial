package com.barry.product.dto.request;

import com.barry.product.modele.InventoryStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

@Data
public class ProductRequest {

    @NotBlank(message = "Le code du produit est obligatoire.")
    private String code;

    @NotBlank(message = "Le nom du produit est obligatoire.")
    private String name;

    private String description;
    private String image;
    private String category;

    @Positive(message = "Le prix doit être un nombre positif.")
    private double price;

    @Min(value = 0, message = "La quantité ne peut pas être négative.")
    private int quantity;

    private String internalReference;
    private int shellId;

    @NotNull(message = "Le statut d'inventaire est obligatoire.")
    private InventoryStatus inventoryStatus;

    @Min(value = 0, message = "La note minimale est 0.")
    @Max(value = 5, message = "La note maximale est 5.")
    private double rating;

    private Instant createdAt;
    private Instant updatedAt;
}
