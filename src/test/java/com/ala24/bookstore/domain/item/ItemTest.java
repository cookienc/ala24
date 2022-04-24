package com.ala24.bookstore.domain.item;

import com.ala24.bookstore.repository.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ItemTest {

	@Autowired
	ItemRepository itemRepository;

	@Test
	void 아이템_저장_테스트() {
	    //given
		SelfDevelopment book = SelfDevelopment.builder()
				.name("JPA")
				.author("김영한")
				.publisher("에이콘 출판")
				.price(38700)
				.stockQuantity(100)
				.build();

		Item savedItem = itemRepository.save(book);

		//when
		Item findItem = itemRepository.findById(savedItem.getId()).stream()
				.findFirst()
				.orElseThrow(NoSuchElementException::new);

		//then
		assertThat(findItem.getName()).isEqualTo("JPA");
		assertThat(findItem.getAuthor()).isEqualTo("김영한");
		assertThat(findItem.getPublisher()).isEqualTo("에이콘 출판");
		assertThat(findItem.getPrice()).isEqualTo(38700);
		assertThat(findItem.getStockQuantity()).isEqualTo(100);
	}
}