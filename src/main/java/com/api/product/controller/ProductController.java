package com.api.product.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import com.api.product.constant.AppConstants;
import com.api.product.dto.ProductDTO;
import com.api.product.model.ProductEntity;
import com.api.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductEntity createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductEntity>> getAllProduct() {
        List<ProductEntity> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getById(@Valid @PathVariable Long id) {
        ProductEntity product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteProduct(@RequestBody ProductDTO productDTO) {
        productService.deleteById(productDTO.getId());
        Map<String, String> response = new HashMap<>();
        response.put("message", AppConstants.DELETE_COMPLETE + productDTO.getId()); 
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductEntity> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductEntity updatedProduct = productService.updateProduct(productDTO);
        return ResponseEntity.ok(updatedProduct);
    }


    @GetMapping
    public ResponseEntity<List<ProductEntity>> getPageProduct(@RequestParam(defaultValue = "1") int page) {
        int size = 10; 
        Page<ProductEntity> pageProduct = productService.getPageProduct(page - 1, size);
        List<ProductEntity> products = pageProduct.getContent();
        return ResponseEntity.ok(products);
    }

    
}
