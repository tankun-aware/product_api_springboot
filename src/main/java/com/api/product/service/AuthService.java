package com.api.product.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.api.product.constant.AppConstants;
import com.api.product.dto.LoginDTO;
import com.api.product.dto.RegisterDTO;
import com.api.product.dto.AppointRoleDTO;
import com.api.product.model.UserEntity;
import com.api.product.repository.RoleRepository;
import com.api.product.repository.UserRepository;
import com.api.product.utils.JwtUtil;
import com.api.product.model.ProductEntity;
import com.api.product.model.RoleEntity;
import com.api.product.enums.ERole;
import com.api.product.exception.JwtResponse;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
	JwtUtil jwtUtil;

    @Autowired
	AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ProfileService userDetailsService;

    public JwtResponse login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        ProfileAuthenticated userDetails = (ProfileAuthenticated) userDetailsService.loadUserByUsername(loginDTO.getUsername());
        
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getPhoneNumber(), userDetails.getAddress(), roles);
    }
    
    public UserEntity register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException(AppConstants.USERNAME_DUPLICATE);
        }

        if (userRepository.existsByPhoneNumber(registerDTO.getPhoneNumber())) {
            throw new RuntimeException(AppConstants.PHONE_DUPLICATE);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setAddress(registerDTO.getAddress());

        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
        .orElseThrow(() -> new RuntimeException(AppConstants.ROLE_NOT_FOUND));
    
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles);
        return userRepository.save(user);
    }


    public UserEntity appointRole(AppointRoleDTO appointRoleDTO) {
        UserEntity user = userRepository.findByUsername(appointRoleDTO.getUsername())
            .orElseThrow(() -> new RuntimeException(AppConstants.USERNAME_NOT_FOUND));
        
        Set<String> strRoles = appointRoleDTO.getRole(); 
        Set<RoleEntity> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role.toLowerCase()) {
                case "user":
                    RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(AppConstants.ROLE_NOT_FOUND));
                    roles.add(userRole);
                    break;
                case "admin":
                    RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(AppConstants.ROLE_NOT_FOUND));
                    roles.add(adminRole);
                    break; 
                default:
                    throw new RuntimeException(AppConstants.ROLE_NOT_FOUND);
            }
        });

        user.setRoles(roles);
        return userRepository.save(user);
    }
}