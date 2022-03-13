package com.ala24.bookstore.service;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.Poem;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

	private static SelfDevelopment book;
	private static Poem poem;

	@Autowired
	ItemService itemService;

	@Autowired
	private DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void init() {
		book = SelfDevelopment.builder()
				.name("JPA")
				.author("김영한")
				.publisher("에이콘 출판")
				.price(38700)
				.stockQuantity(100)
				.build();

		poem = Poem.builder()
				.name("TestName")
				.author("TestAuthor")
				.publisher("TestPublisher")
				.price(12345)
				.stockQuantity(50)
				.build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 아이템_저장_및_조회_테스트() {
	    //given
		Long saveBookId = itemService.saveItem(book);
		Long savePoemId = itemService.saveItem(poem);

		//when
		Item findBook = itemService.findOne(saveBookId);
		Item findPoem = itemService.findOne(savePoemId);
		List<Item> list = itemService.findAll();

		//then
		assertThat(findBook).isEqualTo(book);
		assertThat(findPoem).isEqualTo(poem);
		assertThat(list).contains(book, poem);
		assertThat(list.size()).isEqualTo(2);
	}
}