package com.api.product.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.product.constant.AppConstants;
import com.api.product.dto.ProductDTO;
import com.api.product.model.ProductEntity;
import com.api.product.service.ProductService;




@RestController
@RequestMapping("api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @GetMapping
    public List<ProductEntity> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ProductEntity getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", AppConstants.PRODUCT_ID_NULL));
        }
        
        productService.deleteById(productDTO.getId());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", AppConstants.DELETE_COMPLETE + productDTO.getId());
        
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ProductEntity> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductEntity updatedProduct = productService.updateProduct(productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
    
    
    
    
}
