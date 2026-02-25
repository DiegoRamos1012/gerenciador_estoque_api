package com.diego_ramos.gerenciador_estoque.dto.productDTO;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.utils.Normalizations;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        String newName,

        String newProductCode,

        BigDecimal newPrice,

        Integer newQuantity,

        String newDescription,

        ProductStatus newStatus
) {
    public ProductUpdateDTO {
        newProductCode = Normalizations.trim(newProductCode());
    }
}
