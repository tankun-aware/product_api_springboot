package com.api.product.dto;

import lombok.Data;
import java.util.Set;

import com.api.product.constant.AppConstants;

import jakarta.validation.constraints.*;

@Data
public class RegisterDTO {
    
    @NotBlank(message = AppConstants.USERNAME_NULL)
    @Size(min = 6,max = 20, message = AppConstants.USERNAME_LENGTH)
    private String username;

    @NotBlank(message = AppConstants.PASSWORD_NULL)
    @Size(min = 6, max = 40, message = AppConstants.PASSWORD_LENGTH)
    private String password;

    @NotBlank(message = AppConstants.PHONE_NULL)
    @Pattern(regexp = "[0-9]{10}", message = AppConstants.PHONE_LENGTH)
    private String phoneNumber;

    @Size(max = 1000, message = AppConstants.ADDRESS_LENGTH)
    private String address; 

    private Set<String> role;
}

