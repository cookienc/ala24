package com.ala24.bookstore.service;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.Order;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.Poem;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import com.ala24.bookstore.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

	private static SelfDevelopment book;
	private static Poem poem;
	private static Member memberA;
	private static Member memberB;
	private static Long memberAId;
	private static Long memberBId;
	private static Long bookId;
	private static Long poemId;

	@Autowired
	OrderService orderService;
	@Autowired
	ItemService itemService;
	@Autowired
	MemberService memberService;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void init() {
		Address address = Address.builder()
				.city("서울")
				.address("은마아파트")
				.zipCode(22222)
				.build();

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

		memberA = Member.builder()
				.name("사나")
				.address(address)
				.cash(Cash.charge(100000L))
				.build();

		memberB = Member.builder()
				.name("다현")
				.address(address)
				.cash(Cash.charge(100000L))
				.build();
		bookId = itemService.saveItem(book);
		poemId = itemService.saveItem(poem);
		memberAId = memberService.join(memberA);
		memberBId = memberService.join(memberB);
	}
//
//	@AfterEach
//	void tearDown() {
//		dataBaseCleanup.execute();
//	}

	@Test
	void 상품_주문_테스트() {
	    //given
		Long orderId = orderService.order(memberAId, bookId, 3);

		//when
		Item findItem = itemService.findOne(bookId);
		Order findOrder = orderService.findOrder(orderId);

		//then
		assertThat(findOrder.getMember()).isEqualTo(memberA);
		assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
		assertThat(findOrder.getDelivery().getAddress()).isEqualTo(memberA.getAddress());
	}

//	@Test
//	void 초과_수량_주문_테스트() {
//	    //given
//
//	    //when
//
//	    //then
//	}
}