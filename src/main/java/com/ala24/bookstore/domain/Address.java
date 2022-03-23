package com.ala24.bookstore.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	private String city;
	private Integer zipCode;
	private String specificAddress;

	@Builder
	public Address(String city, int zipCode, String specificAddress) {
		this.city = city;
		this.zipCode = zipCode;
		this.specificAddress = specificAddress;
	}
}
