package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.dto.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::from)
                .toList();
    }
}
