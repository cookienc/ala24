package com.ala24.bookstore.service;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.Order;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.Poem;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import com.ala24.bookstore.domain.type.DeliveryStatus;
import com.ala24.bookstore.domain.type.OrderStatus;
import com.ala24.bookstore.exception.NotEnoughCashException;
import com.ala24.bookstore.exception.NotEnoughItemException;
import com.ala24.bookstore.repository.OrderItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
				.specificAddress("은마아파트")
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
				.price(123456)
				.stockQuantity(100)
				.build();

		memberA = Member.builder()
				.name("사나")
				.address(address)
				.cash(Cash.charge(1000000L))
				.build();

		memberB = Member.builder()
				.name("다현")
				.address(address)
				.cash(Cash.charge(1000000L))
				.build();
		bookId = itemService.saveItem(book);
		poemId = itemService.saveItem(poem);
		memberAId = memberService.join(memberA);
		memberBId = memberService.join(memberB);
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	/**
	 * 주문 테스트
	 */
	@Test
	void 상품_주문_테스트() {
	    //given
		Long orderId = orderService.order(memberAId, bookId, 3);

		//when
		Item findItem = itemService.findOne(bookId);
		Order findOrder = orderService.findOne(orderId);

		//then
		assertThat(findOrder.getMember()).isEqualTo(memberA);
		assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
		assertThat(findOrder.getDelivery().getAddress()).isEqualTo(memberA.getAddress());
	}

	@Test
	void 돈이_부족한_주문() {
		assertThatThrownBy(() -> orderService.order(memberBId, poemId, 50))
				.isInstanceOf(NotEnoughCashException.class);
	}

	@Test
	void 초과_주문_테스트() {
		assertThatThrownBy(() -> orderService.order(memberBId, poemId, 10000))
				.isInstanceOf(NotEnoughItemException.class);
	}

	@Test
	void 잔돈_테스트() {
	    //given
		Long beforeMoney = memberA.account();
		orderService.order(memberAId, bookId, 1);

		//when|
		Long afterMoney = memberService.findOne(memberAId).account();
		Item findItem = itemService.findOne(bookId);

		//then
		assertThat(afterMoney).isEqualTo(beforeMoney - findItem.getPrice() * 1);
	}

	/**
	 * 조회 테스트
	 */
	@Test
	void 단건_주문_조회_테스트() {
	    //given
		Long orderId = orderService.order(memberAId, bookId, 1);

		//when
		Order findOrder = orderService.findOne(orderId);
		Member findMember = memberService.findOne(memberAId);
		Item findItem = itemService.findOne(bookId);

		//then
		assertThat(findOrder.getMember()).isEqualTo(findMember);
		assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
		assertThat(findOrder.getOrderItems().get(0).getItem()).isEqualTo(findItem);
	}

	@Test
	void 모든_주문_조회_테스트() {
	    //given
		Long orderAId = orderService.order(memberAId, bookId, 1);
		Long orderBId = orderService.order(memberBId, poemId, 1);

	    //when
		List<Order> orderList = orderService.findAll();
		Order findOrderA = orderService.findOne(orderAId);
		Order findOrderB = orderService.findOne(orderBId);

		//then
		assertThat(orderList.size()).isEqualTo(2);
		assertThat(orderList).contains(findOrderA, findOrderB);
	}
	
	@Test
	void 주문_취소_테스트() {
	    //given
		int beforeStock = itemService.findOne(bookId).getStockQuantity();
		Long orderId = orderService.order(memberAId, bookId, 10);

		//when
		orderService.cancel(orderId);
		Order findOrder = orderService.findOne(orderId);
		Item findItem = itemService.findOne(bookId);
		//then
		assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
		assertThat(findOrder.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.CANCEL);
		assertThat(findItem.getStockQuantity()).isEqualTo(beforeStock);
	}
}