package com.ala24.bookstore.web.dtos.memberdto;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.strategy.InitialCashStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberFormDto {

	@NotBlank
	private String loginId;

	@NotBlank
	@Size(min = 4, max = 16)
	private String password;

	@NotBlank
	private String name;

	@NotBlank
	private String city;
	@NotBlank
	private String specificAddress;
	@NotNull
	private Integer zipcode;

	public Member toEntity() {
		return Member.builder()
				.name(this.name)
				.loginId(this.loginId)
				.password(this.password)
				.cash(Cash.charge(InitialCashStrategy.NORMAl.getCash()))
				.address(Address.builder()
						.specificAddress(this.specificAddress)
						.city(this.city)
						.zipcode(this.zipcode)
						.build())
				.build();
	}
}
