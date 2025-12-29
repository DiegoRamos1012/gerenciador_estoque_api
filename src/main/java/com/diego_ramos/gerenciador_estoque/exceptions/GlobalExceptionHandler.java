package com.diego_ramos.gerenciador_estoque.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
// Esta é uma classe que captura exceções lançadas pelos controllers e transforma automaticamente as respostas em JSON.
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    // Este @ indica que o método irá tratar especificamente a exceção informada
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) { // Trata qualquer exception não prevista pelo sistema
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado");
    }
}
