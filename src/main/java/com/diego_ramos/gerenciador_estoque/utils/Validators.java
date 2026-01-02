package com.diego_ramos.gerenciador_estoque.utils;

import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;

public final class Validators {
    public static void checkDTOexists(Object dto) {
        if (dto == null) {
            throw new BusinessException(("DTO ausente"));
        }
    }
}
