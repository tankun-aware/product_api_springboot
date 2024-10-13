package com.api.product.dto;

import lombok.Data;

import com.api.product.constant.AppConstants;

import jakarta.validation.constraints.*;

@Data
public class EditProfileDTO {
    private Long id;

    @NotBlank(message = AppConstants.PHONE_NULL)
    @Pattern(regexp = "[0-9]{10}", message = AppConstants.PHONE_LENGTH)
    private String phoneNumber;

    @Size(max = 1000, message = AppConstants.ADDRESS_LENGTH)
    private String address; 
    
}
