package com.ala24.bookstore.domain.strategy;

import lombok.Getter;

/**
 * 회원 가입시 기본 충전 금액 정책을 나타내는 클래스
 */
@Getter
public enum InitialCashStrategy {
	NORMAl(0L);

	private Long cash;

	private InitialCashStrategy(Long standard) {
		this.cash = standard;
	}
}
