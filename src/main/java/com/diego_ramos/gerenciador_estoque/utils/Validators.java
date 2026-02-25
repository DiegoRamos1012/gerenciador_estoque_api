package com.diego_ramos.gerenciador_estoque.utils;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;

public final class Validators {
    private Validators() {
    }

    public static void checkDTOExists(Object dto) {
        if (dto == null) {
            throw new BusinessException(("DTO ausente"));
        }
    }

    public static void validateName(String name) {
        if (name.isBlank()) {
            throw new BusinessException("Nome não deve estar vazio");
        }
    }

    public static void validateEmail(String email) {
        if (email.isBlank()) {
            throw new BusinessException("Email não deve estar vazio");
        }
    }

    public static void validatePassword(String passwordHash) {
        if (passwordHash.isBlank()) {
            throw new BusinessException("Email não deve estar vazio");
        }
    }

    public static void validateRole(UserRole role) {
        if (role == null) {
            throw new BusinessException("A newRole é obrigatória");
        }
    }
}
