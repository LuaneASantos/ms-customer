package com.example.api.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Name is required")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Email is required")
	@Column(nullable = false)
	@NotEmpty
	@Email
	private String email;

	@NotNull(message = "Gender is required")
	@Column(nullable = false)
	@NotEmpty
	private String gender;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", gender='" + gender + '\'' +
				", addresses=" + addresses +
				'}';
	}
}
