package com.ala24.bookstore.web.controller.login;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.service.LoginService;
import com.ala24.bookstore.service.MemberService;
import com.ala24.bookstore.web.dtos.loginDto.LoginFormDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

	public static final String DIFFERENT_PASSWORD = "DifferentPassword";
	public static final String DIFFERENT_LOGIN_ID = "DifferentLoginId";
	private static Member testMember;

	@Autowired
	MockMvc mvc;

	@Autowired
	LoginService loginService;

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
						.zipCode(12345)
						.build()
				).build();

		memberService.join(testMember);
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 로그인_성공_테스트() throws Exception {
		mvc.perform(post("/login")
						.flashAttr("loginForm", new LoginFormDto(testMember.getLoginId(), testMember.getPassword())))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	void 비밀번호_틀림_테스트() throws Exception {
		mvc.perform(post("/login")
						.flashAttr("loginForm", new LoginFormDto(testMember.getLoginId(), DIFFERENT_PASSWORD)))
				.andExpect(status().isOk())
				.andExpect(view().name("login/loginForm"));
	}

	@Test
	void 아이디_틀림_테스트() throws Exception {
		mvc.perform(post("/login")
						.flashAttr("loginForm", new LoginFormDto(DIFFERENT_LOGIN_ID, testMember.getPassword())))
				.andExpect(status().isOk())
				.andExpect(view().name("login/loginForm"));
	}
}