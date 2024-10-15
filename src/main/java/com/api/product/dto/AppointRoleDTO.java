package com.api.product.dto;

import java.util.Set;

import com.api.product.constant.AppConstants;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppointRoleDTO {
    @NotBlank(message = AppConstants.USERNAME_NULL)
    @Size(min = 6,max = 20, message = AppConstants.USERNAME_LENGTH)
    private String username;

    @NotNull(message = AppConstants.ROLE_NULL)
    private Set<String> role;
}
