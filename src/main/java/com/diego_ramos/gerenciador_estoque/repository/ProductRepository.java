package com.diego_ramos.gerenciador_estoque.repository;

import com.diego_ramos.gerenciador_estoque.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository responsável pelo acesso a dados da entidade Product.
 * <p>
 * Métodos herdados do JpaRepository:
 * - save
 * - findById
 * - findAll
 * - deleteById
 * - existsById
 * - count
 * <p>
 * Pode conter queries customizadas quando necessário.
 */

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByProductCodeIgnoreCase(String productCode);
}
