package com.diego_ramos.gerenciador_estoque.dto.userDTO;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserUpdateRoleDTO(
        @NotNull
        UUID id,

        UserRole newRole) {
}
