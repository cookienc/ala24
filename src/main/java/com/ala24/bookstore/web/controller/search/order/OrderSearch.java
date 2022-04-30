package com.ala24.bookstore.web.controller.search.order;

import lombok.Getter;
import lombok.Setter;

/**
 * 주문 조건을 나타내는 클래스
 */
@Getter
@Setter
public class OrderSearch {

	private OrderSearchCondition condition; //주문 검색 기준
	private String data; //검색 내용
}
