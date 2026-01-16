package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.Product;
import com.diego_ramos.gerenciador_estoque.dto.ProductDTO.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.ProductDTO.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.ProductDTO.ProductUpdateDTO;
import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
        UUID inexistentProduct = UUID.randomUUID();

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.existsById(inexistentProduct)).thenReturn(false);

        assertThrows(BusinessException.class, () ->
                productService.deleteProduct(inexistentProduct));

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
        Product fakeProduct1 = ProductFactoryTest.createFakeProduct();
        Product fakeProduct2 = ProductFactoryTest.createFakeProduct();

        List<Product> fakeProducts = List.of(fakeProduct1, fakeProduct2);

        when(productRepository.findAll()).thenReturn(fakeProducts);
        List<ProductResponseDTO> products = assertDoesNotThrow(() -> productService.findAll());
        assertEquals(2, products.size(), "Erro: Deve retornar 2 produtos");
        assertEquals(fakeProduct1.getName(), products.getFirst().name());
        assertEquals(fakeProduct2.getName(), products.getFirst().name());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowBusinessExceptionWhenProductAlreadyExists() {
        Product fakeProduct = ProductFactoryTest.createFakeProduct();

        ProductCreateDTO fakeDto = new ProductCreateDTO(
                fakeProduct.getName(),
                fakeProduct.getPrice(),
                fakeProduct.getQuantity(),
                fakeProduct.getDescription(),
                fakeProduct.getStatus()
        );

        when(productRepository.existsByNameIgnoreCase(fakeDto.name()))
                .thenReturn(true);

        assertThrows(BusinessException.class, () ->
                productService.createProduct(fakeDto));

        verify(productRepository, never()).save(any(Product.class)); // Garantir que o "save" não tenha sido chamado nenhuma vez pra qualquer objeto de Product
    }

    @Test
    void shouldThrowBusinessExceptionWhenProductNotExists() {
        UUID inexistentProduct = UUID.randomUUID();

        when(productRepository.findById(inexistentProduct)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () ->
                productService.findByID(inexistentProduct));

        verify(productRepository, times(1)).findById(inexistentProduct);
    }

    @Test
    void shouldKeepOldValuesWhenUpdateDtoHasNullFields() {
        Product fakeProduct = ProductFactoryTest.createFakeProduct();
        UUID id = fakeProduct.getId();

        when(productRepository.findById(id)).thenReturn(Optional.of(fakeProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductUpdateDTO dto = new ProductUpdateDTO(
                null,
                null,
                null,
                null,
                null
        );

        ProductResponseDTO updated = productService.updateProduct(id, dto);

        assertEquals(fakeProduct.getName(), updated.name());
        assertEquals(fakeProduct.getPrice(), updated.price());
        assertEquals(fakeProduct.getQuantity(), updated.quantity());
        assertEquals(fakeProduct.getDescription(), updated.description());
    }
}
