package com.ala24.bookstore;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.ala24.bookstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 테스트용 데이터가 입력된 함수
 */
@Profile("operation")
@Component
@RequiredArgsConstructor
public class DbSetUpOnOp {

	private final MemberService memberService;

	@PostConstruct
	public void setUp() {
		memberSetUp();
	}

	private void memberSetUp() {
		Member admin = Member.builder()
				.name("admin")
				.loginId("admin")
				.password("admin")
				.cash(Cash.charge(100_000_000L))
				.address(Address.builder()
						.zipcode(12345)
						.city("admin")
						.specificAddress("admin")
						.build())
				.build();
		admin.changeAuthority(MemberStatus.ADMIN);

		memberService.join(admin);
	}
}
