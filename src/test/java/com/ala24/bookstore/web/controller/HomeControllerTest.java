package com.ala24.bookstore.web.controller;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.ala24.bookstore.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

	private static Member testMember;

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

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
						.zipcode(12345)
						.build()
				).build();

		memberService.join(testMember);
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}


	@Test
	void 회원들은_로그인_홈으로_이동() throws Exception {
		//when
		mvc.perform(get("/")
				.sessionAttr("loginMember", testMember))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("loginHome"));
	}

	@Test
	void 어드민일_경우_어드민_홈으로_이동() throws Exception {
	    //given
	    testMember.changeAuthority(MemberStatus.ADMIN);
		//when
		mvc.perform(get("/")
				.sessionAttr("loginMember", testMember))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("adminHome"));
	}

	@Test
	void 로그아웃_테스트() throws Exception {
		//when
		mvc.perform(post("/logout")
						.sessionAttr("loginMember", testMember))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	void 세션이_없을때_로그아웃_테스트() throws Exception {
		//when
		mvc.perform(post("/logout"))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}
}