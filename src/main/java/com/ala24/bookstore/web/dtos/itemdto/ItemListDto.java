package com.ala24.bookstore.web.dtos.itemdto;

import com.ala24.bookstore.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ItemListDto {

	private String name;
	private String author;
	private String publisher;
	private int price;
	private int stockQuantity;

	@Builder
	private ItemListDto(String name, String author, String publisher, int price, int stockQuantity) {
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public List<ItemListDto> toDto(List<Item> items) {
		List<ItemListDto> itemForm = new ArrayList<>();

		for (Item item : items) {
			itemForm.add(ItemListDto.builder()
					.name(item.getName())
					.author(item.getAuthor())
					.publisher(item.getPublisher())
					.price(item.getPrice())
					.stockQuantity(item.getStockQuantity())
					.build());
		}

		return itemForm;
	}
}
