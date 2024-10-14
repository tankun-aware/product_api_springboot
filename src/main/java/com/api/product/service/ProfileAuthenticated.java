package com.api.product.service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.product.model.UserEntity; 
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ProfileAuthenticated implements UserDetails {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String address;

    private String phoneNumber;

    public Collection<? extends GrantedAuthority> authorities;

    public ProfileAuthenticated(
        Long id, 
        String username, 
        String address, 
        String phoneNumber,
        String password,
		Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.username = username;
      this.address = address;
      this.phoneNumber = phoneNumber;
      this.password = password;
      this.authorities = authorities;
    }

    public static ProfileAuthenticated build(UserEntity user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new ProfileAuthenticated(
				user.getId(), 
				user.getUsername(), 
				user.getAddress(),
				user.getPhoneNumber(),
				user.getPassword(), 
				authorities);
        
	  }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
