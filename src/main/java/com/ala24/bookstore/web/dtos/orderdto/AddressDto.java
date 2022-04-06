package com.ala24.bookstore.web.dtos.orderdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

	private String city;
	private String specificAddress;
	private Integer zipcode;
}
