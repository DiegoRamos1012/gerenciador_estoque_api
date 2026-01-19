package com.diego_ramos.gerenciador_estoque.dto.userDTO;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserUpdateDTO(
        @NotNull(message = "O id é obrigatório")
        UUID id,
        
        String name,
        @Email(message = "E-mail inválido")
        @NotBlank(message = "O e-mail é obrigatório")
        String email,


        String password,
        UserRole role) {
}
