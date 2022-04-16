package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.Delivery;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.Order;
import com.ala24.bookstore.domain.OrderItem;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.repository.ItemRepository;
import com.ala24.bookstore.repository.MemberRepository;
import com.ala24.bookstore.repository.OrderRepository;
import com.ala24.bookstore.web.dtos.orderdto.OrderListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.ala24.bookstore.exception.utils.Sentence.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;

	/**
	 * 주문
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
	 * 조회
	 */
	public Order findOne(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new NoSuchElementException(NO_ORDER.toString()));
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public List<OrderListDto> findAllAsList() {
		return findAll().stream()
				.map(OrderListDto::new)
				.collect(Collectors.toList());
	}

	/**
	 * 취소
	 */
	@Transactional
	public void cancel(Long orderId) {
		findOne(orderId).cancel();
	}
}