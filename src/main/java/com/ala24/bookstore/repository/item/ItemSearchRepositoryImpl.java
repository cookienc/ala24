package com.ala24.bookstore.repository.item;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.repository.condition.ItemSearch;
import com.ala24.bookstore.repository.condition.ItemSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
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

		QueryResults<Item> results = queryFactory
				.selectFrom(item)
				.where(findName(condition), findAuthor(condition), findPublisher(condition))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		List<Item> content = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(content, pageable, total);
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
