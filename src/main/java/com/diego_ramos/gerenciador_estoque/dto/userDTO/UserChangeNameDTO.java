package com.diego_ramos.gerenciador_estoque.dto.userDTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserChangeNameDTO(
        @NotNull
        UUID id,

        String newName
) {
}
