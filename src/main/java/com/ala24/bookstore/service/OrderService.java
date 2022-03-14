package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.Delivery;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.Order;
import com.ala24.bookstore.domain.OrderItem;
import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.exception.HaveNotItemException;
import com.ala24.bookstore.repository.ItemRepository;
import com.ala24.bookstore.repository.MemberRepository;
import com.ala24.bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
				.orElseThrow(() -> new IllegalStateException("해당 회원은 존재하지 않습니다."));
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new HaveNotItemException("해당 아이템은 존재하지 않습니다."));

		item.validateOrder(count);

		Delivery delivery = Delivery.createDelivery(member.getAddress());
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		member.validateOrder(orderItem);

		Order order = Order.createOrder(member, delivery, orderItem);

		return orderRepository.save(order).getId();
	}

	/**
	 * 조회
	 */
	public Order findOrder(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new NoSuchElementException("해당 아이템은 없습니다."));
	}

	/**
	 * 취소
	 */
}
