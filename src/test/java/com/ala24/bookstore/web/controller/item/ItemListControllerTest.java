package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
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
class ItemListControllerTest {

	private static Member testMember;
	private static Member admin;

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void setUp() {
		admin = Member.builder()
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
	void ADMIN_아이템_리스트_테스트() throws Exception {
		//given
		memberService.join(admin);

		//when
		mvc.perform(get("/items/list")
						.sessionAttr(LOGIN_MEMBER, admin))
				.andExpect(status().isOk())
				.andExpect(view().name("items/list"));
	}

	@Test
	void USER_아이템_리스트_테스트() throws Exception {
		//given
		memberService.join(testMember);

		//when
		mvc.perform(get("/items/list")
						.sessionAttr(LOGIN_MEMBER, testMember))
				.andExpect(status().isOk())
				.andExpect(view().name("items/list"));
	}
}