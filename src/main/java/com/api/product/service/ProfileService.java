package com.api.product.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.product.dto.EditProfileDTO;
import com.api.product.model.UserEntity;
import com.api.product.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfileService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username : " + username));

        return ProfileAuthenticated.build(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity editProfile(String username, EditProfileDTO editProfileDTO) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setPhoneNumber(editProfileDTO.getPhoneNumber());
            user.setAddress(editProfileDTO.getAddress());
            return userRepository.save(user);
        }
        return null;
    }

}
