package com.diego_ramos.gerenciador_estoque.dto.userDTO;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;

import java.util.UUID;

public record UserUpdateDTO(UUID id, String name, String email, String password, UserRole role) {
}
