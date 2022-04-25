package com.ala24.bookstore.web.dtos.memberdto;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberListDto {

	private Long id;
	private String name;
	private String loginId;
	private Address address;
	private Cash cash;
	private MemberStatus authority;

	@Builder
	public MemberListDto(Member member) {
		this.id = member.getId();
		this.name = member.getName();
		this.loginId = member.getLoginId();
		this.address = member.getAddress();
		this.cash = member.getCash();
		this.authority = member.getAuthority();
	}
}
