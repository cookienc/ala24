package com.ala24.bookstore.web.dtos.itemdto;

import lombok.Getter;

@Getter
public enum ItemCategory {
	NOVEL("소설"), POEM("시"), SELF_DEVELOPMENT("자기계발서");

	private String description;

	ItemCategory(String description) {
		this.description = description;
	}
}
