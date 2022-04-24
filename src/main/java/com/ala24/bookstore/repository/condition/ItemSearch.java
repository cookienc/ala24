package com.ala24.bookstore.repository.condition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemSearch {

	private ItemSearchCondition condition;
	private String name;
}
