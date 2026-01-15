package com.diego_ramos.gerenciador_estoque.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
// Define que esta classe não será uma tabela, mas seus campos serão herdados pelas entidades filhas (extends)
public abstract class AuditableEntity {
    protected LocalDateTime createdAt;
    protected LocalDateTime lastTimeChanged;

    /**
     * Executa automaticamente antes da entidade ser salva no banco (INSERT)
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastTimeChanged = createdAt;
    }

    /**
     * Executa automaticamente antes da entidade exisente receber uma atualização no banco (UPDATE)
     */
    @PreUpdate
    protected void onUpdate() {
        lastTimeChanged = LocalDateTime.now();
    }

}
