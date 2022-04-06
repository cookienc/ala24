package com.ala24.bookstore.web.dtos.orderdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderFormDto {

	private Long itemId;
	private String itemName;
	private Integer price;
	private Integer quantity;

	private Long memberId;
	private String memberName;
	private AddressDto address;
}
