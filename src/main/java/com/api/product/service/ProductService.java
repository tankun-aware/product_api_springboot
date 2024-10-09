package com.api.product.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.api.product.constant.AppConstants;
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
            throw new IllegalArgumentException(AppConstants.PRODUCT_NAME_NULL);
        }
        
        if (productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException(AppConstants.PRICE_MORE_ZERO);
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
            throw new IllegalArgumentException(AppConstants.PRODUCT_ID_NULL);
        }

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        productRepository.deleteById(id);
        return (AppConstants.DELETE_COMPLETE + id); 
    }

    public ProductEntity updateProduct(ProductDTO productDTO) {
        ProductEntity product = modelMapper.map(productDTO, ProductEntity.class);
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException(AppConstants.PRODUCT_NAME_NULL);
        }
        
        if (productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException(AppConstants.PRICE_MORE_ZERO);
        }
        
        productRepository.findById(product.getId())
            .orElseThrow(() -> new ResourceAccessException(AppConstants.PRODUCT_NOT_FOUND + product.getId()));

        return productRepository.save(product);
    }


}
