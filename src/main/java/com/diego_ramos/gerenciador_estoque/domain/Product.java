package com.diego_ramos.gerenciador_estoque.domain;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import jakarta.persistence.*;
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
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    private LocalDateTime lastTimeChanged;

    // 🔒 Construtor interno
    private Product(String name, BigDecimal price, Integer quantity, String description) {
        validateQuantity(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.status = quantity == 0 ? ProductStatus.OUT_OF_STOCK : ProductStatus.ACTIVE;
    }

    // 🏭 Factory
    public static Product create(String name, BigDecimal price, Integer quantity, String description) {
        return new Product(name, price, quantity, description);
    }

    // 🔁 Atualização principal
    public void update(
            String name,
            BigDecimal price,
            Integer quantity,
            String description,
            ProductStatus newStatus
    ) {
        validateQuantity(quantity);

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;

        if (quantity == 0) {
            this.status = ProductStatus.OUT_OF_STOCK;
        } else if (newStatus != null) {
            changeStatus(newStatus);
        }

        updateLastTimeChanged();
    }

    public void changeStatus(ProductStatus newStatus) {
        if (newStatus == null) return;

        if (this.status == ProductStatus.OUT_OF_STOCK && newStatus == ProductStatus.ACTIVE && quantity == 0) {
            throw new BusinessException("Produto sem estoque não pode ser ativado");
        }

        this.status = newStatus;
        updateLastTimeChanged();
    }

    private void validateQuantity(Integer quantity) {
        if (quantity < 0) {
            throw new BusinessException("Quantidade não pode ser negativa");
        }
    }

    private void updateLastTimeChanged() {
        this.lastTimeChanged = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
