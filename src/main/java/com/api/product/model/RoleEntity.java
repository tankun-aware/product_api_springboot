package com.api.product.model;

import com.api.product.model.base.BaseEntity;
import com.api.product.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends BaseEntity{

  @Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
    
}
