package com.api.product.exception;

import lombok.Data;

@Data
public class MessageResponse {

    private Object data;
    private String status;

    public MessageResponse(Object data, String status) {
        this.data = data;
        this.status = status;
    }
}
