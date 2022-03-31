package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.web.dtos.itemdto.ItemCategory;
import com.ala24.bookstore.web.dtos.itemdto.ItemFormDto;
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
class ItemPostControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	void 상품_등록_폼으로_이동_테스트() throws Exception {
		mvc.perform(get("/items/post"))
				.andExpect(status().isOk())
				.andExpect(view().name("items/postItemForm"));
	}

	@Test
	void 상품_등록_성공_테스트() throws Exception {
		mvc.perform(post("/items/post")
				.flashAttr("itemForm", new ItemFormDto("test", "test", "test",
						ItemCategory.NOVEL, 15000, 999)))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/items/list"));
	}
}