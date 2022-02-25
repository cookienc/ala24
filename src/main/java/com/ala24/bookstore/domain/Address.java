package com.ala24.bookstore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	private String city;
	private Integer zipCode;
	private String address;

	@Builder
	public Address(String city, int zipCode, String address) {
		this.city = city;
		this.zipCode = zipCode;
		this.address = address;
	}
}
