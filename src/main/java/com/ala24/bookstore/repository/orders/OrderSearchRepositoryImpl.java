package com.ala24.bookstore.repository.orders;

import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.orders.condition.OrderSearch;
import com.ala24.bookstore.domain.orders.condition.OrderSearchCondition;
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
import static com.ala24.bookstore.domain.orders.condition.OrderSearchCondition.*;
import static com.ala24.bookstore.domain.type.DeliveryStatus.*;

public class OrderSearchRepositoryImpl implements OrderSearchRepository{

	private final JPAQueryFactory queryFactory;

	public OrderSearchRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

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
		long total = queryFactory.selectFrom(order)
				.join(order.member, member).fetchJoin()
				.where(findMemberName(condition), findItemName(condition), findDeliveryStatus(condition))
				.where(findMemberId(loginId))
				.fetchCount();

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression findMemberId(String loginId) {
		return loginId != null ?
				member.loginId.eq(loginId) : null;
	}

	private BooleanExpression findMemberName(OrderSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, MEMBER_NAME) ?
				member.name.containsIgnoreCase(condition.getData()) : null;
	}

	private BooleanExpression findItemName(OrderSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, ITEM_NAME) ?
				item.name.containsIgnoreCase(condition.getData()) : null;
	}

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

	private boolean isItSameCondition(OrderSearch condition, OrderSearchCondition cond) {
		return condition.getCondition().equals(cond);
	}

	private boolean isNotNullData(OrderSearch condition) {
		return condition.getData() != null;
	}
}
