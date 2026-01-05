package com.diego_ramos.gerenciador_estoque.dto;

import com.diego_ramos.gerenciador_estoque.domain.Product;
import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,

        String name,

        BigDecimal price,

        Integer quantity,

        String description,

        ProductStatus status,

        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        LocalDateTime lastTimeChanged
) {
    public static ProductResponseDTO from(Product product) {
        if (product == null) return null;
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                product.getStatus(),
                product.getLastTimeChanged()
        );
    }
}
