package com.barry.product.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@ToString
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @UuidGenerator
    private String id;
    private String name;
}