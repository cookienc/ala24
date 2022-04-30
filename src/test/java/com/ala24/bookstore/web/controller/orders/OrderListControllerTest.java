package com.ala24.bookstore.web.controller.orders;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class OrderListControllerTest {

	private static Member test;

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void setUp() {
		Address address = Address.builder()
				.zipcode(12345)
				.city("Seoul")
				.specificAddress("Apartment")
				.build();

		test = Member.builder()
				.name("test")
				.loginId("test")
				.password("test")
				.cash(Cash.charge(0L))
				.address(address)
				.build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 주문리스트_화면으로_이동() throws Exception {
	    //given
		memberService.join(test);
	    //when
		mvc.perform(get("/orders/list")
						.sessionAttr(LOGIN_MEMBER, test))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("orders/list"));
	}
}