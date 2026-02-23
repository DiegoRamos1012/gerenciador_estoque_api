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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.diego_ramos.gerenciador_estoque.utils.Validators.checkDTOExists;

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
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public void create(@RequestBody @Valid ProductCreateDTO dto) {
        checkDTOExists(dto);
        productService.createProduct(dto);
    }

    @GetMapping
    @Operation(summary = "Lista todos os produtos")
    public List<ProductResponseDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ProductResponseDTO findById(@PathVariable UUID id) {
        return productService.findByID(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza produto por ID")
    public ProductResponseDTO updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductUpdateDTO dto) {
        checkDTOExists(dto);
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

}
