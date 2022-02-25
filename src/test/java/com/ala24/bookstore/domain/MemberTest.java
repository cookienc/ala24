package com.ala24.bookstore.domain;

import com.ala24.bookstore.repository.MemberRepository;
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

	@Autowired
	MemberRepository memberRepository;

	@Test
	void 멤버_저장_테스트() {
	    //given
		Address address = Address.builder()
				.city("서울")
				.address("은마아파트")
				.zipCode(22222)
				.build();

		Member memberA = Member.builder()
				.name("사나")
				.address(address)
				.build();

		Member memberB = Member.builder()
				.name("다현")
				.address(address)
				.build();

		Member savedMemberA = memberRepository.save(memberA);
		Member savedMemberB = memberRepository.save(memberB);

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

}