package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.Product;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.productDTO.ProductUpdateDTO;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import com.diego_ramos.gerenciador_estoque.repository.ProductRepository;
import com.diego_ramos.gerenciador_estoque.utils.Validators;
import org.jspecify.annotations.NonNull;
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

        // validações centralizadas
        Validators.validateProductName(dto.name());
        Validators.validateProductCode(dto.productCode());

        if (productRepository.existsByNameIgnoreCase(dto.name())) {
            throw new BusinessException("Já existe um produto com este nome");
        }

        if (productRepository.existsByProductCodeIgnoreCase(dto.productCode())) {
            throw new BusinessException("Já existe um produto com este código");
        }

        Product product = Product.create(
                dto.name(),
                dto.productCode(),
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

    public ProductResponseDTO updateProduct(UUID id, @NonNull ProductUpdateDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        // validar campos opcionais quando presentes
        if (dto.newName() != null) {
            Validators.validateProductName(dto.newName());
            if (!dto.newName().equalsIgnoreCase(product.getName()) && productRepository.existsByNameIgnoreCase(dto.newName())) {
                throw new BusinessException("Já existe um produto com este nome");
            }
        }

        if (dto.newProductCode() != null) {
            Validators.validateProductCode(dto.newProductCode());
            if (!dto.newProductCode().equalsIgnoreCase(product.getProductCode()) && productRepository.existsByProductCodeIgnoreCase(dto.newProductCode())) {
                throw new BusinessException("Já existe um produto com este código");
            }
        }

        product.update(
                dto.newName() != null ? dto.newName() : product.getName(),
                dto.newProductCode() != null ? dto.newProductCode() : product.getProductCode(),
                dto.newPrice() != null ? dto.newPrice() : product.getPrice(),
                dto.newQuantity() != null ? dto.newQuantity() : product.getQuantity(),
                dto.newDescription() != null ? dto.newDescription() : product.getDescription(),
                dto.newStatus() != null ? dto.newStatus() : product.getStatus()
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
