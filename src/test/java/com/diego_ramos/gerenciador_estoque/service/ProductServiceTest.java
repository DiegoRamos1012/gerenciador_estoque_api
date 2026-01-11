package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.Product;
import com.diego_ramos.gerenciador_estoque.dto.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.ProductUpdateDTO;
import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.extension.TestLoggerExtension;
import com.diego_ramos.gerenciador_estoque.factory.ProductFactoryTest;
import com.diego_ramos.gerenciador_estoque.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, TestLoggerExtension.class})

public class ProductServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testProductCreation() {
        Product fakeProduct = ProductFactoryTest.createFakeProduct();

        ProductCreateDTO fakeDto = new ProductCreateDTO(
                fakeProduct.getName(),
                fakeProduct.getPrice(),
                fakeProduct.getQuantity(),
                fakeProduct.getDescription(),
                fakeProduct.getStatus()
        );

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertDoesNotThrow(() -> productService.createProduct(fakeDto));
        verify(productRepository, times(1)).save(any(Product.class));


    }

    @Test
    void testProductRemoval() {
        UUID productId = UUID.randomUUID();

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);

    }

    @Test
    void testProductUpdate() {
        Product fakeProduct = ProductFactoryTest.createFakeProduct();
        UUID fakeId = fakeProduct.getId();

        when(productRepository.findById(fakeId)).thenReturn(Optional.of(fakeProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductUpdateDTO dto = new ProductUpdateDTO(
                "Produto Atualizado",           // name
                BigDecimal.valueOf(250.00),    // price
                30,                             // quantity
                "Produto atualizado para teste",// description
                ProductStatus.ACTIVE            // status
        );

        ProductResponseDTO updatedProduct = assertDoesNotThrow(() -> productService.updateProduct(fakeId, dto));

        assertEquals("Produto Atualizado", updatedProduct.name());
        assertEquals(0, updatedProduct.price().compareTo(BigDecimal.valueOf(250.00)));
        assertEquals(30, updatedProduct.quantity());
        assertEquals("Produto atualizado para teste", updatedProduct.description());
        assertEquals(ProductStatus.ACTIVE, updatedProduct.status());

        verify(productRepository, times(1)).save(any(Product.class));

    }

    @Test
    void testProductList() {
        // TODO: Inserir teste pra verificar se utiliza o findById e findAll

    }
}
