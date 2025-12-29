package com.diego_ramos.gerenciador_estoque.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException() {
        super("Erro detectado");
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
