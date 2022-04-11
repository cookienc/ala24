package com.ala24.bookstore.web.dtos.memberdto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashFormDto {

	private String name;
	private String loginId;
	private Long currentAccount;
	private Long chargeMoney;

	@Builder
	public CashFormDto(String name, String loginId, Long currentAccount, Long chargeMoney) {
		this.name = name;
		this.loginId = loginId;
		this.currentAccount = currentAccount;
		this.chargeMoney = chargeMoney;
	}
}
