package com.ala24.bookstore.web.dtos.itemdto;

import com.ala24.bookstore.domain.item.Item;
import com.querydsl.core.annotations.QueryProjection;
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
	@QueryProjection
	public ItemListDto(Long itemId, String name, String author, String publisher, int price, int stockQuantity) {
		this.itemId = itemId;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public ItemListDto(Item item) {
		this.itemId = item.getId();
		this.name = item.getName();
		this.author = item.getAuthor();
		this.publisher = item.getPublisher();
		this.price = item.getPrice();
		this.stockQuantity = item.getStockQuantity();
	}
}
