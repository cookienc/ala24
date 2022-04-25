package com.ala24.bookstore.repository.item;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.repository.condition.ItemSearch;
import com.ala24.bookstore.repository.condition.ItemSearchCondition;
import com.ala24.bookstore.repository.condition.ItemSortCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ala24.bookstore.domain.item.QItem.item;
import static com.ala24.bookstore.repository.condition.ItemSearchCondition.*;

public class ItemSearchRepositoryImpl implements ItemSearchRepository{

	private final JPAQueryFactory queryFactory;

	public ItemSearchRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Item> searchPage(ItemSearch condition, Pageable pageable) {

		JPAQuery<Item> query = queryFactory
				.selectFrom(item)
				.where(findName(condition), findAuthor(condition), findPublisher(condition))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize());

		if (condition.getSortCondition() != null) {
			query.orderBy(sortMethod(condition));
		}

		QueryResults<Item> results = query.fetchResults();
		List<Item> content = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(content, pageable, total);
	}

	private OrderSpecifier<?> sortMethod(ItemSearch condition) {
		ItemSortCondition type = condition.getSortCondition();
		Order method = findOrderMethod(condition.getSort());

		switch (type) {
			case PRICE:
				return new OrderSpecifier<>(method, item.price);
		}
		return null;
	}

	private Order findOrderMethod(String sort) {
		return sort.equals("ASC") ? Order.ASC : Order.DESC;
	}

	private BooleanExpression findName(ItemSearch condition) {
		return isItSameCondition(condition, NAME) && isNotNullData(condition) ?
				item.name.containsIgnoreCase(condition.getData()) : null;
	}

	private BooleanExpression findAuthor(ItemSearch condition) {
		return isItSameCondition(condition, AUTHOR) && isNotNullData(condition) ?
				item.author.containsIgnoreCase(condition.getData()) : null;
	}

	private BooleanExpression findPublisher(ItemSearch condition) {
		return isItSameCondition(condition, PUBLISHER) && isNotNullData(condition) ?
				item.publisher.containsIgnoreCase(condition.getData()) : null;
	}

	private boolean isNotNullData(ItemSearch condition) {
		return condition.getData() != null;
	}

	private boolean isItSameCondition(ItemSearch condition, ItemSearchCondition cond) {
		return condition.getCondition().equals(cond);
	}
}
