package com.api.product.dto;

import com.api.product.constant.AppConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = AppConstants.USERNAME_NULL)
    private String username;
    @NotBlank(message = AppConstants.PASSWORD_NULL)
    private String password;
}
