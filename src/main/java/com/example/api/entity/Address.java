package com.example.api.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String street;
	private Long number;
	private String city;
	private String country;
	private String zipCode;
	private String complement;
	private String neighborhood;
	private String state;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", street='" + street + '\'' +
				", number=" + number +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", zipCode='" + zipCode + '\'' +
				", complement='" + complement + '\'' +
				", neighborhood='" + neighborhood + '\'' +
				", state='" + state + '\'' +
				", customer=" + customer +
				'}';
	}
}
