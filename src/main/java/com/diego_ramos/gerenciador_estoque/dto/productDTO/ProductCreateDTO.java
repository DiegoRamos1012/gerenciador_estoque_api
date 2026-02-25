package com.diego_ramos.gerenciador_estoque.dto.productDTO;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.utils.Normalizations;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductCreateDTO(
        @NotNull
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotNull
        String productCode,

        @NotNull
        @Positive
        BigDecimal price,

        @PositiveOrZero
        int quantity,

        String description,

        ProductStatus status
) {
    public ProductCreateDTO {
        productCode = Normalizations.trim(productCode());
    }
}
