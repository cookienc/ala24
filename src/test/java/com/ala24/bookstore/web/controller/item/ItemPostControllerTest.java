package com.ala24.bookstore.web.controller.item;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.web.dtos.itemdto.ItemCategory;
import com.ala24.bookstore.web.dtos.itemdto.ItemFormDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemPostControllerTest {

	private static MultiValueMap<String, String> params;

	private static Stream<Arguments> sampleData() {
		ItemFormDto normalForm = new ItemFormDto("test", "test", "test",
				ItemCategory.NOVEL, 15_000, 999);
		ItemFormDto noName = new ItemFormDto("", "test", "test",
						ItemCategory.NOVEL, 15_000, 999);
		ItemFormDto noAuthor = new ItemFormDto("test", "", "test",
						ItemCategory.NOVEL, 15_000, 999);
		ItemFormDto noPublisher = new ItemFormDto("test", "test", "",
						ItemCategory.NOVEL, 15_000, 999);
		ItemFormDto noPrice = new ItemFormDto("test", "test", "test",
						ItemCategory.NOVEL, 0, 999);
		ItemFormDto outOfMinRangePrice = new ItemFormDto("test", "test", "test",
						ItemCategory.NOVEL, 999, 999);
		ItemFormDto outOfMaxRangePrice = new ItemFormDto("test", "test", "test",
						ItemCategory.NOVEL, 100_001, 999);
		ItemFormDto noStockQuantity = new ItemFormDto("test", "test", "test",
						ItemCategory.NOVEL, 15_000, 0);
		ItemFormDto outOfMinRangeStockQuantity = new ItemFormDto("test", "test", "test",
						ItemCategory.NOVEL, 15_000, 9);
		ItemFormDto outOfMaxRangeStockQuantity = new ItemFormDto("test", "test", "test",
						ItemCategory.NOVEL, 15_000, 10_000);

		return Stream.of(
				Arguments.of(normalForm, 0),
				Arguments.of(noName, 1),
				Arguments.of(noAuthor, 1),
				Arguments.of(noPublisher, 1),
				Arguments.of(noPrice, 1),
				Arguments.of(noStockQuantity, 1),
				Arguments.of(outOfMinRangePrice, 1),
				Arguments.of(outOfMaxRangePrice, 1),
				Arguments.of(outOfMinRangeStockQuantity, 1),
				Arguments.of(outOfMaxRangeStockQuantity, 1)
		);
	}

	@Autowired
	private MockMvc mvc;

	@Autowired
	private Validator validator;

	@Autowired
	ItemPostController itemPostController;

	@Autowired
	private DataBaseCleanup dataBaseCleanup;

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@BeforeEach
	public void setMvc() {
		mvc = MockMvcBuilders.standaloneSetup(itemPostController).build();
	}

	@BeforeEach
	void setUp() {
		params = new LinkedMultiValueMap<>();
		params.add("name", "test");
		params.add("author", "test");
		params.add("publisher", "test");
		params.add("category", "NOVEL");
		params.add("price", "15000");
		params.add("stockQuantity", "999");
	}

	@Test
	void 상품_등록_폼으로_이동_테스트() throws Exception {
		//given
		//when
		mvc.perform(get("/items/post"))
		//then
				.andExpect(status().isOk())
				.andExpect(view().name("items/postItemForm"));
	}

	@Test
	void 상품_등록_성공_테스트() throws Exception {
		//given
		//when
		mvc.perform(post("/items/post")
						.params(params)
				)
		//then
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/items/list"));
	}

	@ParameterizedTest
	@MethodSource("sampleData")
	void 검증_테스트(ItemFormDto itemForm, int count) {
	    //given
		Set<ConstraintViolation<ItemFormDto>> violations = validator.validate(itemForm);
		//when
	    //then
		Assertions.assertThat(violations.size()).isEqualTo(count);
	}
}