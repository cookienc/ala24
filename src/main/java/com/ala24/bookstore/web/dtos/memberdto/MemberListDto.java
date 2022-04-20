package com.ala24.bookstore.web.dtos.memberdto;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.type.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MemberListDto {

	private String name;
	private String loginId;
	private Address address;
	private Cash cash;
	private MemberStatus authority;

	public MemberListDto() {

	}

	@Builder
	public MemberListDto(String name, String loginId, Address address, Cash cash, MemberStatus authority) {
		this.name = name;
		this.loginId = loginId;
		this.address = address;
		this.cash = cash;
		this.authority = authority;
	}
}
