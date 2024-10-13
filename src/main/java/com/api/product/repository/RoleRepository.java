package com.api.product.repository;


import org.springframework.stereotype.Repository;

import com.api.product.model.RoleEntity;
import com.api.product.enums.ERole;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long>{
    Optional<RoleEntity> findByName(ERole name);
}
