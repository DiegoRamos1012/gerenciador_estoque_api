package com.diego_ramos.gerenciador_estoque.dto;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        String newName,

        BigDecimal newPrice,

        Integer newQuantity,

        String newDescription,

        ProductStatus newStatus
) {
}
