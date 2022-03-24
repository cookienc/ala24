package com.ala24.bookstore.service;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class LoginServiceTest {

	@Autowired
	LoginService loginService;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	private static Member testMember;

	@BeforeEach
	void init() {
		testMember = Member.builder()
				.name("test")
				.loginId("test")
				.password("1234")
				.cash(Cash.charge(0L))
				.address(Address.builder()
						.city("서울")
						.specificAddress("강남")
						.zipCode(12345)
						.build()
				).build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 로그인_성공_테스트() {
	    //given
		memberService.join(testMember);

		//when
		Member loginMember = loginService.login(testMember.getLoginId(), testMember.getPassword());

		//then
		assertThat(loginMember.getLoginId()).isEqualTo(testMember.getLoginId());
		assertThat(loginMember.getPassword()).isEqualTo(testMember.getPassword());
		assertThat(loginMember.getName()).isEqualTo(testMember.getName());
		assertThat(loginMember.getAddress().getCity()).isEqualTo(testMember.getAddress().getCity());
		assertThat(loginMember.getAddress().getZipCode()).isEqualTo(testMember.getAddress().getZipCode());
		assertThat(loginMember.getAddress().getSpecificAddress()).isEqualTo(testMember.getAddress().getSpecificAddress());
	}
	
	@Test
	void 아이디가_틀렸을_경우() {
	    //given
		memberService.join(testMember);

	    //when
	    //then
		assertThatThrownBy(() -> loginService.login("differentId", testMember.getPassword()))
				.isInstanceOf(NoSuchElementException.class);
	}
	
	@Test
	void 비밀번호가_틀렸을_경우() {
	    //given
		memberService.join(testMember);
	    //when
	    //then
		assertThatThrownBy(() -> loginService.login(testMember.getLoginId(), "differentPassWord"))
				.isInstanceOf(IllegalArgumentException.class);
	}
}