package com.ala24.bookstore.web.dtos.orderdto;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class MemberOrderFormDto {

	private Long memberId;
	private String memberName;
	private Address address;

	public MemberOrderFormDto toDto(Member member) {
		MemberOrderFormDto memberOrderForm = new MemberOrderFormDto();
		memberOrderForm.setMemberId(member.getId());
		memberOrderForm.setMemberName(member.getName());
		memberOrderForm.setAddress(member.getAddress());

		return memberOrderForm;
	}
}
