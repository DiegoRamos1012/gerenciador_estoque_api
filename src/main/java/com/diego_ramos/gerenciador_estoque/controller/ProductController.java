// java
package com.diego_ramos.gerenciador_estoque.controller;

import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductUpdateDTO;
import com.diego_ramos.gerenciador_estoque.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Operações relacionadas a produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid ProductCreateDTO dto) {

        productService.createProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Produto criado com sucesso!"
                ));
    }

    @GetMapping
    @Operation(summary = "Lista todos os produtos")
    public ResponseEntity<List<ProductResponseDTO>> findAll() {

        List<ProductResponseDTO> products = productService.findAll();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable UUID id) {

        ProductResponseDTO product = productService.findByID(id);

        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza produto por ID")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductUpdateDTO dto) {

        ProductResponseDTO updatedProduct = productService.updateProduct(id, dto);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}