package com.ala24.bookstore;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.ala24.bookstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DbSetUp {

	private final MemberService memberService;
	private final DataBaseCleanup dataBaseCleanup;

	@PostConstruct
	public void setUp() {
		memberSetUp();
	}

	@PreDestroy
	public void tearDown() {
		dataBaseCleanup.execute();
	}

	private void memberSetUp() {
		Member test = Member.builder()
				.name("test")
				.loginId("test")
				.password("test")
				.cash(Cash.charge(0L))
				.address(Address.builder()
						.zipCode(12345)
						.city("Seoul")
						.specificAddress("Apartment")
						.build())
				.build();
		memberService.join(test);

		Member admin = Member.builder()
				.name("admin")
				.loginId("admin")
				.password("admin")
				.build();
		admin.changeAuthority(MemberStatus.ADMIN);

		memberService.join(admin);
	}


}
