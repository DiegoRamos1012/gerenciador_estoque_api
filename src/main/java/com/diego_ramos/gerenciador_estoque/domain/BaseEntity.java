package com.diego_ramos.gerenciador_estoque.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
// Define que esta classe não será uma tabela, mas seus campos serão herdados pelas entidades filhas (extends)
public abstract class AuditableEntity {

    protected LocalDateTime createdAt;
    protected LocalDateTime lastTimeChanged;
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false, length = 250)
    private String name;

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
