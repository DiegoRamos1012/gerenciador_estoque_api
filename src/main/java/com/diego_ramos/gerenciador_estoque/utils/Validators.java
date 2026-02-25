package com.diego_ramos.gerenciador_estoque.utils;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;

public final class Validators {
    private Validators() {
    }

    public static void validateUserName(String name) {
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
            throw new BusinessException("Senha não deve estar vazia");
        }
    }

    public static void validateRole(UserRole role) {
        if (role == null) {
            throw new BusinessException("A newRole é obrigatória");
        }
    }

    // Validações para Product

    public static void validateProductName(String name) {
        if (name == null || name.isBlank()) {
            throw new BusinessException("Não é possível registrar um produto com nome vazio");
        }
    }

    public static void validateProductCode(String productCode) {
        if (productCode == null || productCode.isBlank()) {
            throw new BusinessException("Não é possível registrar um produto com código vazio");
        }
    }
}
