package com.diego_ramos.gerenciador_estoque.dto.authDTO;

import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserResponseDTO;

public record AuthResponseDTO(
        String accessToken,
        String tokenType,
        UserResponseDTO user
) {
}
