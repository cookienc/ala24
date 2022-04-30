package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.Delivery;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.orders.OrderItem;
import com.ala24.bookstore.web.controller.search.order.OrderSearch;
import com.ala24.bookstore.repository.item.ItemRepository;
import com.ala24.bookstore.repository.member.MemberRepository;
import com.ala24.bookstore.repository.orders.OrderRepository;
import com.ala24.bookstore.web.dtos.orderdto.OrderListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ala24.bookstore.web.controller.search.order.OrderSearchCondition.NORMAL;
import static com.ala24.bookstore.exception.utils.Sentence.*;

/**
 * 주문과 관련된 함수를 가지고 있는 서비스 계층
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;

	/**
	 * 주문을 실행하는 함수
	 * @param memberId 주문할 회원 아이디
	 * @param itemId 주문할 아이템 아이디
	 * @param count 주문할 아이템 개수
	 * @return 주문 아이디
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NoSuchElementException(NO_MEMBER.toString()));
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new NoSuchElementException(NO_ITEM.toString()));


		Delivery delivery = Delivery.createDelivery(member.getAddress());

		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		member.validateOrder(orderItem);

		Order order = Order.createOrder(member, delivery, orderItem);

		return orderRepository.save(order).getId();
	}

	/**
	 * 주문 조회 홤수
	 * @param orderId 주문 조회 아이디
	 * @return 주문 내역
	 */
	public Order findOne(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new NoSuchElementException(NO_ORDER.toString()));
	}

	/**
	 * 주문 전체 조회
	 * @return 주문 전체 내용의 리스트
	 */
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	/**
	 * 검색 조건을 이용하여 페이징된 주문을 조회, OrderListDto로 변환하여 반환
	 * @param condition 주문 검색 조건
	 * @param loginId 회원의 로그인 아이디 -> null이면 전체 조회
	 * @param pageable 페이징 조건
	 * @return 페이징된 주문 내역을 OrderListDto로 변환하여 반환
	 */
	public Page<OrderListDto> findAllFetch(OrderSearch condition, String loginId, Pageable pageable) {

		if (condition.getCondition() == null) {
			condition.setCondition(NORMAL);
		}

		if (loginId == null) {
			return orderRepository.searchPage(condition, null, pageable)
					.map(OrderListDto::new);
		}

		return orderRepository.searchPage(condition, loginId, pageable)
				.map(OrderListDto::new);
	}

	/**
	 * 주문을 찾아서 취소
	 * @param orderId 취소할 주문의 아이디
	 */
	@Transactional
	public void cancel(Long orderId) {
		findOne(orderId).cancel();
	}
}