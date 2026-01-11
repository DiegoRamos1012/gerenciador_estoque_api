package com.diego_ramos.gerenciador_estoque.factory;

import com.diego_ramos.gerenciador_estoque.domain.Product;

import java.math.BigDecimal;

/**
 * Factory para gerar um produto falso para ser usado pelos testes.
 * Não pode ser acessado por outros packages.
 */
public class ProductFactoryTest {
    public static Product createFakeProduct() {
        return Product.create("Produto Teste", BigDecimal.valueOf(220.00), 20, "Produto para testes");
    }
}
