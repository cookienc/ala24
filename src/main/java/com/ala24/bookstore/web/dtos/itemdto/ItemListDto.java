package com.ala24.bookstore.web.dtos.itemdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemListDto {

	private Long itemId;
	private String name;
	private String author;
	private String publisher;
	private int price;
	private int stockQuantity;

	@Builder
	private ItemListDto(Long itemId, String name, String author, String publisher, int price, int stockQuantity) {
		this.itemId = itemId;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
}
