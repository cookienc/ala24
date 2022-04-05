package com.ala24.bookstore.web.dtos.orderdto;

import com.ala24.bookstore.domain.item.Item;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ItemOrderFormDto {

	public static final int NORMAL_QUANTITY = 1;
	private String itemName;
	private int price;
	private int quantity;

	@Builder
	public ItemOrderFormDto(String itemName, int price, int quantity) {
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}

	public ItemOrderFormDto toDto(Item item) {
		return ItemOrderFormDto.builder()
				.itemName(item.getName())
				.price(item.getPrice())
				.quantity(NORMAL_QUANTITY)
				.build();
	}
}
