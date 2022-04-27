package com.ala24.bookstore.domain;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * 회원의 주소를 나타내는 클래스
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	private String city;
	private Integer zipcode;
	private String specificAddress;

	@Builder
	public Address(String city, Integer zipcode, String specificAddress) {
		this.city = city;
		this.zipcode = zipcode;
		this.specificAddress = specificAddress;
	}
}
