package com.diego_ramos.gerenciador_estoque.controller;

import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductUpdateDTO;
import com.diego_ramos.gerenciador_estoque.enums.ProductStatus;
import com.diego_ramos.gerenciador_estoque.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductCreateDTO dto = new ProductCreateDTO(
                "Produto teste",
                "ABC-1234",
                BigDecimal.valueOf(250.00),
                30,
                "Produto teste",
                ProductStatus.ACTIVE
        );

        doNothing().when(productService).createProduct(any(ProductCreateDTO.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(productService, times(1)).createProduct(any(ProductCreateDTO.class));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        UUID id = UUID.randomUUID();

        ProductUpdateDTO dto = new ProductUpdateDTO(
                "Produto Teste",
                "DEF-5678",
                BigDecimal.valueOf(300.32),
                50,
                "Produto para testes",
                ProductStatus.OUT_OF_STOCK
        );

        mockMvc.perform(patch("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(productService, times(1)).updateProduct(eq(id), any(ProductUpdateDTO.class));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().is2xxSuccessful());

        verify(productService, times(1)).deleteProduct(id);
    }

    @Test
    void shouldListProducts() throws Exception {
        ProductResponseDTO dto1 = mock(ProductResponseDTO.class);
        ProductResponseDTO dto2 = mock(ProductResponseDTO.class);

        when(productService.findAll()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2));

        verify(productService, times(1)).findAll();
    }

    @Test
    void shouldFindProductById() throws Exception {
        // TODO: Criar teste pra testar método findbyId()
    }
}
