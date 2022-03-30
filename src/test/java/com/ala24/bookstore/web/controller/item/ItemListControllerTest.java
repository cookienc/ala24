package com.ala24.bookstore.web.controller.item;

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
class ItemListControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	void 아이템_리스트_테스트() throws Exception {
		mvc.perform(get("/items/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("items/list"));
	}
}