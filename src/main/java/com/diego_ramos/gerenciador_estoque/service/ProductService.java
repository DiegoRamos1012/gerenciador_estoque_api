package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.Product;
import com.diego_ramos.gerenciador_estoque.dto.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.ProductUpdateDTO;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import com.diego_ramos.gerenciador_estoque.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductCreateDTO dto) {
        if (productRepository.existsByNameIgnoreCase(dto.name())) {
            throw new BusinessException("Já existe um produto com este nome");
        }

        Product product = Product.create(
                dto.name(),
                dto.price(),
                dto.quantity(),
                dto.description()
        );

        productRepository.save(product);
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::from)
                .toList();
    }

    public ProductResponseDTO findByID(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        return ProductResponseDTO.from(product);
    }

    public ProductResponseDTO updateProduct(UUID id, ProductUpdateDTO dto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        product.update(
            dto.newName() != null ? dto.newName() : product.getName(),
            dto.newPrice() != null ? dto.newPrice() : product.getPrice(),
            dto.newQuantity() != null ? dto.newQuantity() : product.getQuantity(),
            dto.newDescription() != null ? dto.newDescription() : product.getDescription(),
            dto.newStatus()
    );

    productRepository.save(product);
    return ProductResponseDTO.from(product);
}


        // return ProductResponseDTO.from(product);
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new BusinessException("Produto não encontrado");
        }

        productRepository.deleteById(id);
    }
}
