package com.diego_ramos.gerenciador_estoque.domain;

import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Representa um produto no sistema de gerenciamento de estoque.
 * <p>
 * Herda campos e comportamento de BaseEntity:
 * - id
 * - name
 * - createdAt
 * - lastTimeChanged
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    // =====================
    // Atributos específicos
    // =====================

    @Column(nullable = false, unique = true, length = 100)
    private String productCode;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    // =====================
    // Construtores
    // =====================

    /**
     * Construtor privado para uso interno e factory method.
     */
    private Product(String name, BigDecimal price, Integer quantity, String description) {
        validateQuantity(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.status = quantity == 0 ? ProductStatus.OUT_OF_STOCK : ProductStatus.ACTIVE;
    }

    // =====================
    // Factory
    // =====================

    /**
     * Cria uma nova instância de Product.
     *
     * @param name        Nome do produto
     * @param price       Preço do produto
     * @param quantity    Quantidade em estoque
     * @param description Descrição opcional
     * @return Produto criado
     */
    public static Product create(String name, BigDecimal price, Integer quantity, String description) {
        return new Product(name, price, quantity, description);
    }

    // =====================
    // Métodos de negócio
    // =====================

    /**
     * Atualiza os principais atributos do produto.
     *
     * @param name        Novo nome
     * @param price       Novo preço
     * @param quantity    Nova quantidade
     * @param description Nova descrição
     * @param newStatus   Novo status, pode ser null
     */
    public void update(String name, BigDecimal price, Integer quantity, String description, ProductStatus newStatus) {
        validateQuantity(quantity);

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;

        if (quantity == 0) {
            this.status = ProductStatus.OUT_OF_STOCK;
        } else if (newStatus != null) {
            changeStatus(newStatus);
        }

        updateLastTimeChanged();
    }

    /**
     * Altera o status do produto seguindo regras de negócio.
     *
     * @param newStatus Novo status
     */
    public void changeStatus(ProductStatus newStatus) {
        if (newStatus == null) return;

        if (this.status == ProductStatus.OUT_OF_STOCK && newStatus == ProductStatus.ACTIVE && quantity == 0) {
            throw new BusinessException("Produto sem estoque não pode ser ativado");
        }

        this.status = newStatus;
        updateLastTimeChanged();
    }

    // =====================
    // Validações e utilitários internos
    // =====================

    /**
     * Valida se a quantidade é válida (não negativa).
     *
     * @param quantity Quantidade a validar
     */
    private void validateQuantity(Integer quantity) {
        if (quantity < 0) {
            throw new BusinessException("Quantidade não pode ser negativa");
        }
    }

    @Override
    protected void updateLastTimeChanged() {
        super.updateLastTimeChanged();
    }

    /**
     * Atualiza o campo lastTimeChanged para o horário atual.
     */

    // =====================
    // Overrides
    // =====================
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
