package com.api.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.api.product.constant.AppConstants;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super(AppConstants.PRODUCT_NOT_FOUND  + id);
    }
}
 