package com.ala24.bookstore.web.controller.members;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.controller.member.MemberListController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.ala24.bookstore.web.session.SessionAttributeName.LOGIN_MEMBER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberListControllerTest {

	private static Member admin;
	private static Member testMember;
	@Autowired
	MemberService memberService;

	@Autowired
	MockMvc mvc;

	@Autowired
	MemberListController memberListController;

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
	void 회원_조회_화면으로_이동_권한O() throws Exception {
		//given
		memberService.join(admin);

		//when
		mvc.perform(get("/members/list")
						.sessionAttr(LOGIN_MEMBER, admin))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("members/list"));
	}
}