package com.ala24.bookstore.domain.item.condition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemSearch {

	private ItemSearchCondition condition;
	private ItemSortCondition sortCondition;
	private String data;
	private String sort;
}