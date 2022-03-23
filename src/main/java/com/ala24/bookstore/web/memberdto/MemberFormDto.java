package com.ala24.bookstore.web.memberdto;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class MemberFormDto {

	@NotEmpty(message = "회원 이름은 필수 입니다.")
	private String loginId;

	@NotEmpty(message = "비밀번호는 4 ~ 16 자리여야 합니다.")
	@Size(min = 4, max = 16)
	private String password;

	@NotEmpty(message = "이름은 필수 입니다.")
	private String name;

	private String city;
	private String specificAddress;
	private Integer zipcode;

	public MemberFormDto() {

	}

	public Member toEntity() {
		return Member.builder()
				.name(this.name)
				.loginId(this.loginId)
				.password(this.password)
				.address(Address.builder()
						.specificAddress(this.specificAddress)
						.city(this.city)
						.zipCode(this.zipcode)
						.build())
				.build();
	}
}
