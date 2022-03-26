package com.ala24.bookstore.web.dtos.memberdto;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberListDto {

	private String name;
	private String loginId;
	private Address address;
	private Cash cash;
	private MemberStatus authority;

	public List<MemberListDto> toDto(List<Member> members) {

		List<MemberListDto> list = new ArrayList<>();

		for (Member member : members) {
			this.name = member.getName();
			this.loginId = member.getLoginId();
			this.address = member.getAddress();
			this.cash = member.getCash();
			this.authority = member.getAuthority();
			list.add(this);
		}

		return list;
	}
}
