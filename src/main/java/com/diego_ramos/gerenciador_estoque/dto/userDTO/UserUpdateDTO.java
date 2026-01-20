package com.diego_ramos.gerenciador_estoque.dto.userDTO;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserUpdateDTO(
        @NotNull(message = "O id é obrigatório")
        UUID id,

        @Size(min = 3)
        String name,

        @Email
        String email,

        @Size(min = 8)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$",
                message = "Senha deve conter letra maiúscula, número e caractere especial"
        )
        String password,

        UserRole role) {
}
