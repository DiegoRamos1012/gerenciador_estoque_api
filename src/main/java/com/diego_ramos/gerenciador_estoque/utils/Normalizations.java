package com.diego_ramos.gerenciador_estoque.utils;

public final class Normalizations {
    private Normalizations() {
    }

    // Remove espaços de uma string
    public static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
