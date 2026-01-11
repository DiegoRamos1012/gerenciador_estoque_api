package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.Product;
import com.diego_ramos.gerenciador_estoque.dto.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testSuccessfulProductCreation() {
        ProductCreateDTO dto = new ProductCreateDTO("Produto Teste", BigDecimal.valueOf(220.20),20,"Produto criado para testes", ProductStatus.ACTIVE);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertDoesNotThrow(() -> productService.createProduct(dto));

        verify(productRepository, times(1)).save(any(Product.class));
    }
}
