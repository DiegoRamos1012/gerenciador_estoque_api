package com.diego_ramos.gerenciador_estoque.domain;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue()
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    @NotNull
    @NotBlank
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @Positive(message = "Preço deve ser maior que zero")
    private BigDecimal price;

    @PositiveOrZero
    @Column(nullable = false)
    @NotNull
    private int quantity;

    @Column
    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private ProductStatus status;

    @Column(name = "last_time_changed")
    private LocalDateTime lastTimeChanged;

    // Construtor
    private Product(
            String name,
            BigDecimal price,
            int quantity,
            String description
    ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    // Factory

    public static Product create(
            String name,
            BigDecimal price,
            int quantity,
            String description
    ) {
        if (quantity < 0) {
            throw new BusinessException("Quantidade não pode ser negativa");
        }

        Product product = new Product(name, price, quantity, description);
        product.status = ProductStatus.ACTIVE;
        product.lastTimeChanged = LocalDateTime.now();

        return product;
    }

    @PrePersist
    @PreUpdate
    private void prePersistAndUpdate() {
        // Define status padrão ao criar
        if (this.status == null) {
            this.status = ProductStatus.ACTIVE;
        }
        // Atualiza o timestamp sempre que persistir ou atualizar
        this.lastTimeChanged = LocalDateTime.now();
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.quantity = newQuantity;
    }

    @Override
    public boolean equals(Object o) { // Método para comparar objetos da mesma classe, considerando apenas "id" para igualdade
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() { // Gera código hash que o Java usa em HashSet, HashMap e outras coleções
        return getClass().hashCode();
    }
}
