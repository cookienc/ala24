package com.ala24.bookstore.web.controller;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MemberControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberService memberService;

	@Autowired
	private DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 회원가입_폼_출력_확인() throws Exception {
		this.mvc.perform(get("/members/post"))
				.andExpect(status().isOk())
				.andExpect(view().name("members/postMemberForm"));
	}
}