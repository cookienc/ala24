package com.ala24.bookstore.domain;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.ala24.bookstore.repository.member.MemberRepository;
import com.ala24.bookstore.service.CashService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberTest {

	private static final long ZERO = 0L;
	private static Member memberA;
	private static Member memberB;

	private static Member savedMemberA;
	private static Member savedMemberB;
	private static Cash memberACash;
	private static Cash memberBCash;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	CashService cashService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void init() {
		memberACash = Cash.charge(ZERO);
		memberBCash = Cash.charge(ZERO);

		Address address = Address.builder()
				.city("서울")
				.specificAddress("은마아파트")
				.zipcode(22222)
				.build();

		memberA = Member.builder()
				.name("사나")
				.address(address)
				.cash(memberACash)
				.build();

		memberB = Member.builder()
				.name("다현")
				.address(address)
				.cash(memberBCash)
				.build();

		savedMemberA = memberRepository.save(memberA);
		savedMemberB = memberRepository.save(memberB);
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 멤버_저장_테스트() {
		//given

		//when
		Member findMemberA = memberRepository.findById(savedMemberA.getId()).get();
		Member findMemberB = memberRepository.findById(savedMemberB.getId()).get();

		//then
		assertThat(findMemberA.getId()).isEqualTo(memberA.getId());
		assertThat(findMemberB.getId()).isEqualTo(memberB.getId());

		assertThat(findMemberA.getName()).isEqualTo(memberA.getName());
		assertThat(findMemberB.getName()).isEqualTo(memberB.getName());

		assertThat(findMemberA.getAddress()).isEqualTo(memberA.getAddress());
		assertThat(findMemberB.getAddress()).isEqualTo(memberB.getAddress());

		assertThat(findMemberA.getAuthority()).isEqualTo(MemberStatus.USER);
		assertThat(findMemberB.getAuthority()).isEqualTo(MemberStatus.USER);
	}
}