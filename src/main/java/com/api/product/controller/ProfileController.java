package com.api.product.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.product.service.ProfileService;
import com.api.product.model.UserEntity;
import com.api.product.exception.MessageResponse;
import com.api.product.dto.EditProfileDTO;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/account/profile")
public class ProfileController {
	@Autowired
	ProfileService userDetailsService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> allAccess() {
        List<UserEntity> users = userDetailsService.getAllUsers();
        return ResponseEntity.ok(new MessageResponse(users, "success"));
    }

	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> userAccess() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = userDetailsService.loadUserByUsername(userDetails.getUsername());
		return ResponseEntity.ok(new MessageResponse(user, "success"));
	}

	@PostMapping("/edit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateProfile(@RequestBody EditProfileDTO editProfileDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity editProfile = userDetailsService.editProfile(username, editProfileDTO);

        if (editProfile != null) {
            return ResponseEntity.ok(new MessageResponse(editProfile, "success"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse(null, "FAIL"));
        }
    }
}
