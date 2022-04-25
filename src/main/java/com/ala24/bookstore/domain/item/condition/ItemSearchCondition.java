package com.ala24.bookstore.domain.item.condition;

import lombok.Getter;

@Getter
public enum ItemSearchCondition {
	NORMAL("분류"), NAME("이름"),
	AUTHOR("저자"), PUBLISHER("출판사");

	private String description;

	ItemSearchCondition(String description) {
		this.description = description;
	}
}
