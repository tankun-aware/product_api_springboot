package com.api.product.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.api.product.constant.AppConstants;
import com.api.product.dto.ProductDTO;
import com.api.product.model.ProductEntity;
import com.api.product.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    

    public ProductEntity createProduct(ProductDTO productDTO) { 
        productDTO.setId(null);
        ProductEntity product = modelMapper.map(productDTO, ProductEntity.class);
        return productRepository.save(product);
    }

    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }

    public ProductEntity getById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(AppConstants.PRODUCT_NOT_FOUND));
    } 

    public String deleteById(Long id) {
        if (id == null) {
            throw new RuntimeException(AppConstants.PRODUCT_ID_NULL);
        }

        if (!productRepository.existsById(id)) {
            throw new RuntimeException(AppConstants.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
        return (AppConstants.DELETE_COMPLETE + id); 
    }

    public ProductEntity updateProduct(ProductDTO productDTO) {
        ProductEntity product = modelMapper.map(productDTO, ProductEntity.class);
        
        productRepository.findById(product.getId())
            .orElseThrow(() -> new RuntimeException(AppConstants.PRODUCT_NOT_FOUND));
        return productRepository.save(product);
    }

    public Page<ProductEntity> getPageProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }



}
