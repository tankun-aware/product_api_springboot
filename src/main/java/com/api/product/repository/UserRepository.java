package com.api.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.product.model.RoleEntity;
import com.api.product.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByRoles(RoleEntity role);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(String phoneNumber);
    
}
