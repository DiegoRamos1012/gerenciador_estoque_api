package com.diego_ramos.gerenciador_estoque.dto.userDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
) {
}
