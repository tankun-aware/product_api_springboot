package com.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.product.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    
}
