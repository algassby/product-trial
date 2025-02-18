package com.barry.product.modele;

import com.barry.product.audit.Audit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;
    private String image;
    private String category;

    private double price;
    private int quantity;

    @Column(name = "internal_reference")
    private String internalReference;

    @Column(name = "shell_id")
    private int shellId;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status", nullable = false)
    private InventoryStatus inventoryStatus;

    private int rating;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != 0 && Objects.equals(id, product.id);
    }

    @PrePersist
    @PreUpdate
    public void updateStatusBeforeSave() {
        updateInventoryStatus();
    }

    public void updateInventoryStatus() {
        if (this.quantity > 10) {
            this.inventoryStatus = InventoryStatus.INSTOCK;
        } else if (this.quantity > 0) {
            this.inventoryStatus = InventoryStatus.LOWSTOCK;
        } else {
            this.inventoryStatus = InventoryStatus.OUTOFSTOCK;
        }
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
