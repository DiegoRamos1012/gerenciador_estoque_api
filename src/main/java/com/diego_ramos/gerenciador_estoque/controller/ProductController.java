package com.diego_ramos.gerenciador_estoque.controller;

import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductUpdateDTO;
import com.diego_ramos.gerenciador_estoque.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.diego_ramos.gerenciador_estoque.utils.Validators.checkDTOExists;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid ProductCreateDTO dto) {
        checkDTOExists(dto);
        productService.createProduct(dto);
    }

    @GetMapping
    public List<ProductResponseDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable UUID id) {
        return productService.findByID(id);
    }

    @PatchMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductUpdateDTO dto) {
        checkDTOExists(dto);
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

}
