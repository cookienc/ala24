package com.ala24.bookstore.domain.item.condition;

import lombok.Getter;

@Getter
public enum ItemSortCondition {
	PRICE("가격");

	private final String description;

	ItemSortCondition(String description) {
		this.description = description;
	}
}
