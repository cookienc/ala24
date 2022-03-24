package com.ala24.bookstore.domain.strategy;

import lombok.Getter;

@Getter
public enum InitialCashStrategy {
	NORMAl(0L);

	private Long cash;

	private InitialCashStrategy(Long standard) {
		this.cash = standard;
	}
}
