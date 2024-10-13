package com.api.product.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map; 

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        responseBody.put("status", "FAIL"); 
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, String> errorMessages = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessages.put(error.getField(), error.getDefaultMessage());
        });
    
        if (errorMessages.size() == 1) {
            responseBody.put("error", errorMessages.values().iterator().next());
        } else {
            responseBody.put("error", errorMessages);
        }
        
        responseBody.put("status", "FAIL");
    
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseBody);
    }
    

}


