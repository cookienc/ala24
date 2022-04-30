package com.ala24.bookstore.repository.orders;

import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.web.controller.search.order.OrderSearch;
import com.ala24.bookstore.web.controller.search.order.OrderSearchCondition;
import com.ala24.bookstore.domain.type.DeliveryStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ala24.bookstore.domain.QDelivery.delivery;
import static com.ala24.bookstore.domain.item.QItem.item;
import static com.ala24.bookstore.domain.member.QMember.member;
import static com.ala24.bookstore.domain.orders.QOrder.order;
import static com.ala24.bookstore.domain.orders.QOrderItem.orderItem;
import static com.ala24.bookstore.web.controller.search.order.OrderSearchCondition.*;
import static com.ala24.bookstore.domain.type.DeliveryStatus.*;

/**
 * 주문 검색 기능을 가진 인터페이스를 queryDsl로 구현한 구현체
 */
public class OrderSearchRepositoryImpl implements OrderSearchRepository{

	private final JPAQueryFactory queryFactory;

	public OrderSearchRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	/**
	 * 주문 검색 조건과 로그인 아이디를 이용하여 검색된 주문은 페이징이 가능한 형태로 반환
	 * @param condition 주문 검색 조건
	 * @param loginId 회원의 로그인 아이디
	 * @param pageable 페이징
	 * @return Page로 변환된 order
	 */
	@Override
	public Page<Order> searchPage(OrderSearch condition, String loginId, Pageable pageable) {
		QueryResults<Order> results = queryFactory.selectFrom(order)
				.join(order.orderItems, orderItem).fetchJoin()
				.join(order.delivery, delivery).fetchJoin()
				.join(order.member, member).fetchJoin()
				.join(orderItem.item, item).fetchJoin()
				.where(findMemberName(condition),findItemName(condition), findDeliveryStatus(condition))
				.where(findMemberId(loginId))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.distinct()
				.fetchResults();

		List<Order> content = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(content, pageable, total);
	}

	/**
	 * data와 일치하는 로그인 아이디의 조건을 반환
	 * @param loginId 검색 조건
	 * @return data와 일치하는 조건식을 반환
	 */
	private BooleanExpression findMemberId(String loginId) {
		return loginId != null ?
				member.loginId.eq(loginId) : null;
	}

	/**
	 * condition의 data가 포함하는 문자를 가진 이름의 조건을 반환
	 * @param condition 검색 조건
	 * @return data를 포함하는 이름의 조건식을 반환
	 */
	private BooleanExpression findMemberName(OrderSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, MEMBER_NAME) ?
				member.name.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 포함하는 상품의 이름을 가진 이름의 조건을 반환
	 * @param condition 검색 조건
	 * @return data를 포함하는 상품의 이름의 조건식을 반환
	 */
	private BooleanExpression findItemName(OrderSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, ITEM_NAME) ?
				item.name.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 포함하는 문자와 일치하는 주문 상태의 조건을 반환
	 * @param condition 검색 조건
	 * @return data와 일치하는 주문 상태의 조건식을 반환
	 */
	private BooleanExpression findDeliveryStatus(OrderSearch condition) {
		if (!isNotNullData(condition) || !isItSameCondition(condition, DELIVERY_STATUS)) {
			return null;
		}

		DeliveryStatus status = changeToDeliveryStatus(condition.getData());
		if (status == null) {
			return null;
		}

		return delivery.deliveryStatus.eq(status);
	}

	/**
	 * data의 값을 DeliveryStatus로 변환
	 * @param data deliveryStatus 중 한 개
	 * @return 일치하는 deliveryStatus
	 */
	private DeliveryStatus changeToDeliveryStatus(String data) {

		switch (data) {
			case "주문 취소":
				return CANCEL;
			case "배송 준비":
				return PREPARING;
			case "배송 중":
				return SHIPPING;
			case "배달 완료":
				return COMPLETE;
		}
		return null;
	}

	/**
	 * OrderSearch안의 OrderSearchCondition의 데이터가 OrderSearchConditon과 같은 값인지 확인
	 * @param condition 검색 조건
	 * @param cond 비교군
	 * @return 데이터가 일치하면 true, 없으면 false
	 */
	private boolean isItSameCondition(OrderSearch condition, OrderSearchCondition cond) {
		return condition.getCondition().equals(cond);
	}

	/**
	 * OrderSearch안의 OrderSearchCondition의 데이터가 Null 값인지 확인
	 * @param condition 검색 조건
	 * @return 데이터가 있으면 true, 없으면 false
	 */
	private boolean isNotNullData(OrderSearch condition) {
		return condition.getData() != null;
	}
}
