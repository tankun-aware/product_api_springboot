package com.api.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.product.model.UserEntity;
import com.api.product.dto.LoginDTO;
import com.api.product.dto.RegisterDTO;
import com.api.product.exception.JwtResponse;
import com.api.product.exception.MessageResponse;
import com.api.product.service.AuthService;
import com.api.product.utils.JwtUtil;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/account")
public class AuthController {

    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
    private AuthService authService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        JwtResponse response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }


	@PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) { 
		UserEntity user = authService.register(registerDTO);
		return ResponseEntity.ok(new MessageResponse(user, "success"));
    }

}
