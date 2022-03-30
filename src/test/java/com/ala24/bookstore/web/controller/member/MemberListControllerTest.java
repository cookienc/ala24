package com.ala24.bookstore.web.controller.member;

import com.ala24.bookstore.DataBaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class MemberListControllerTest {

	@Autowired
	MemberListController memberListController;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@Autowired
	MockMvc mvc;

	@BeforeEach
	void setMvc() {
		mvc = MockMvcBuilders.standaloneSetup(memberListController).build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 회원_조회_화면으로_이동() throws Exception {
		mvc.perform(get("/members/list"))
				.andExpect(view().name("members/list"));
	}
}