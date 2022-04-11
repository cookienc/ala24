package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class MemberCashControllerTest {

	private static Member testMember;

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void setUp() {
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
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 충전_폼으로_이동() throws Exception {
	    //given
		memberService.join(testMember);
	    //when
		mvc.perform(get("/members/cash")
				.sessionAttr("loginMember", testMember))
	    //then
				.andExpect(status().isOk())
				.andExpect(view().name("members/cash"));
	}
}