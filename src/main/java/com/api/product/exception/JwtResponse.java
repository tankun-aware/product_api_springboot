package com.api.product.exception;


import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String phoneNumber;
    private String address;
    private List<String> roles;

    public JwtResponse(
        String accessToken, 
        Long id, 
        String username, 
        String phoneNumber, 
        String address,  
        List<String> roles
    )
    {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }
}
