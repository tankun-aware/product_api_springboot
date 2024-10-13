package com.api.product.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.annotation.*;

import java.util.HashSet;
import java.util.Set;

import com.api.product.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(	name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserEntity extends BaseEntity{

	@Nonnull
	@Column(unique = true)
	private String username;

	@Nonnull
	@JsonIgnore
	private String password;

	@Column(length = 1000)
	private String address;

	@Nonnull
	@Column(length = 10)
	private String phoneNumber;

  	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles = new HashSet<>();
}
