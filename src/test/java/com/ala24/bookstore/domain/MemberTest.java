package com.ala24.bookstore.domain;

import com.ala24.bookstore.repository.MemberRepository;
import com.ala24.bookstore.service.CashService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

	private static final long ZERO = 0L;
	private static Member memberA;
	private static Member memberB;

	private static Member savedMemberA;
	private static Member savedMemberB;
	private static Cash memberACash = Cash.charge(ZERO);
	private static Cash memberBCash = Cash.charge(ZERO);


	@Autowired
	MemberRepository memberRepository;
	@Autowired
	CashService cashService;

	@BeforeEach
	void init() {
		Address address = Address.builder()
				.city("서울")
				.address("은마아파트")
				.zipCode(22222)
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
	}

	@Test
	void 돈_충전_테스트() {
	    //given
		cashService.charge(savedMemberA.getId(), 10000L);
		//when
		Member findMember = memberRepository.findById(savedMemberA.getId()).get();

		//then
		assertThat(findMember.remainCash()).isEqualTo(10000L);
	}
}