package com.ala24.bookstore.web.dtos.memberdto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CashFormDto {

	private String name;
	private String loginId;
	private Long currentAccount;

	@NotNull
	@Range(min = 5_000, max = 100_000)
	private Long chargeMoney;

	@Builder
	public CashFormDto(String name, String loginId, Long currentAccount, Long chargeMoney) {
		this.name = name;
		this.loginId = loginId;
		this.currentAccount = currentAccount;
		this.chargeMoney = chargeMoney;
	}
}
