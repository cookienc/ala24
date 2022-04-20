package com.ala24.bookstore.web.pages;

import lombok.Getter;

@Getter
public enum DefaultPageButtonSize {
	DEFAULT_PAGE_BUTTON_RANGE(5);

	private Integer pageNum;

	DefaultPageButtonSize(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
