package com.diego_ramos.gerenciador_estoque.dto;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(
        @NotNull
        UUID id,

        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        BigDecimal price,

        @PositiveOrZero
        int quantity,

        String description,

        @NotNull
        ProductStatus status,

        @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
        LocalDateTime lastTimeChanged) {
}
