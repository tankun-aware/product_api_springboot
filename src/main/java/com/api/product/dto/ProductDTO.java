package com.api.product.dto;

import java.math.BigDecimal;

import com.api.product.constant.AppConstants;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;

    @NotNull(message = AppConstants.PRODUCTNAME_NULL)
    private String name;

    @NotNull(message = AppConstants.PRICE_NULL)
    @Min(value = 0, message = AppConstants.PRICE_MORE_ZERO)
    private BigDecimal price;

    @Size(max = 2000, message = AppConstants.DRESCRIPTION_LENGTH)
    private String description;
}
