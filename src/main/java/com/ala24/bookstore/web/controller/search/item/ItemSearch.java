package com.ala24.bookstore.web.controller.search.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 아이템 검색 조건을 가지는 클래스
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ItemSearch {

	private ItemSearchCondition condition; //검색 기준
	private ItemSortCondition sortCondition; //정렬 기준
	private String data; //값
	private String sort; //정렬 값 -> asc, desc
}
