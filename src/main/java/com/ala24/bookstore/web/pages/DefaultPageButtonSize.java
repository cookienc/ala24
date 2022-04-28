package com.ala24.bookstore.web.pages;

import lombok.Getter;

/**
 * 페이지 버튼의 크기를 나타내는 클래스
 */
@Getter
public enum DefaultPageButtonSize {
	DEFAULT_PAGE_BUTTON_RANGE(5);

	private Integer pageNum;

	DefaultPageButtonSize(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
