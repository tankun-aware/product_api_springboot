package com.api.product.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.api.product.dto.ProductDTO;
import com.api.product.exception.ResourceNotFoundException;
import com.api.product.model.ProductEntity;
import com.api.product.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductEntity createProduct(ProductDTO productDTO) { 
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        
        if (productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        
        ProductEntity product = modelMapper.map(productDTO, ProductEntity.class);
        return productRepository.save(product);
    }

    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }

    public ProductEntity getById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
    } 

    public String deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID must be provided.");
        }

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        productRepository.deleteById(id);
        return "Product with id " + id + " has been successfully deleted."; 
    }

    public ProductEntity updateProduct(ProductDTO productDTO) {
        ProductEntity product = modelMapper.map(productDTO, ProductEntity.class);
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        
        if (productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        
        productRepository.findById(product.getId())
            .orElseThrow(() -> new ResourceAccessException("Product not found with id: " + product.getId()));

        return productRepository.save(product);
    }


}
