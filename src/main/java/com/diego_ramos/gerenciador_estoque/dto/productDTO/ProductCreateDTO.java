// java
package com.diego_ramos.gerenciador_estoque.dto.productDTO;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.utils.Normalizations;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        @NotBlank(message = "productCode é obrigatório")
        String productCode,

        @NotNull
        @Positive
        BigDecimal price,

        @PositiveOrZero
        int quantity,

        String description,

        ProductStatus status
) {
    @JsonCreator
    public ProductCreateDTO(
            @JsonProperty("name") String name,
            @JsonProperty("productCode") String productCode,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("description") String description,
            @JsonProperty("status") ProductStatus status
    ) {
        // normalizações usando os parâmetros recebidos
        String normalizedName = name == null ? null : Normalizations.trim(name);
        String normalizedProductCode = productCode == null ? null : Normalizations.trim(productCode);

        this.name = normalizedName;
        this.productCode = normalizedProductCode;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.status = status;
    }
}