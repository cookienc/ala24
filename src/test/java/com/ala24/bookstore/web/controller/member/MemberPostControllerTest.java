package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import javax.validation.Validator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberPostControllerTest {

	private static LinkedMultiValueMap<String, String> params;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private Validator validator;

	@Autowired
	private MemberService memberService;

	@Autowired
	private DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@BeforeEach
	public void setUp() {
		params = new LinkedMultiValueMap<>();
		params.add("loginId", "test");
		params.add("password", "test");
		params.add("name", "test");
		params.add("city", "test");
		params.add("specificAddress", "test");
		params.add("zipcode", "12345");
	}

	@Test
	void 회원가입_폼_출력_확인() throws Exception {
		//given
		//when
		this.mvc.perform(get("/members/post"))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("members/postMemberForm"));
	}

	@Test
	void 회원가입_성공() throws Exception {
		//given
		//when
		this.mvc.perform(post("/members/post")
				.params(params))
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	void 회원가입_실패시_원래_화면으로_돌아감() throws Exception {
		//given
		params.remove("name");
		params.add("name", "");
		//when
		this.mvc.perform(post("/members/post")
				.params(params))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("members/postMemberForm"));
	}
}