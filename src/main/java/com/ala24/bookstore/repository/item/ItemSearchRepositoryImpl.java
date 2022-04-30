package com.ala24.bookstore.repository.item;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.web.controller.search.item.ItemSearch;
import com.ala24.bookstore.web.controller.search.item.ItemSearchCondition;
import com.ala24.bookstore.web.controller.search.item.ItemSortCondition;
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
import static com.ala24.bookstore.web.controller.search.item.ItemSearchCondition.*;

/**
 * ItemSearchRepository를 구현한 저장소 클래스
 * querydsl을 사용하여 구현
 */
public class ItemSearchRepositoryImpl implements ItemSearchRepository{

	private final JPAQueryFactory queryFactory;

	public ItemSearchRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	/**
	 * querydsl로 페이징 및 검색 조건을 구현
	 * @param condition 검색 조건
	 * @param pageable 페이징 조건
	 * @return Page<Item>
	 */
	@Override
	public Page<Item> searchPage(ItemSearch condition, Pageable pageable) {

		JPAQuery<Item> query = queryFactory
				.selectFrom(item)
				.where(findName(condition), findAuthor(condition), findPublisher(condition)) //검색 조건
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

	/**
	 * condition의 sortCondition과 sort가 있으면 정렬 조건을 반환하는 클래스
	 * @param condition 정렬 조건
	 * @return 정렬 조건
	 */
	private OrderSpecifier<?> sortMethod(ItemSearch condition) {
		ItemSortCondition type = condition.getSortCondition();
		Order method = findOrderMethod(condition.getSort());

		switch (type) {
			case PRICE:
				return new OrderSpecifier<>(method, item.price);
		}
		return null;
	}

	/**
	 * 정렬 내용을 보고 오름차순, 내림차순으로 변경
	 * @param sort 정렬 조건
	 * @return querydsl로 사용할 수 있는 정렬 조건
	 */
	private Order findOrderMethod(String sort) {
		return sort.equals("ASC") ? Order.ASC : Order.DESC;
	}

	/**
	 * condition의 data가 포함하는 문자를 가진 이름의 조건식을 반환
	 * @param condition 이름의 검색 조건
	 * @return 값이 있으면 조건을, 값이 없으면 null 반환
	 */
	private BooleanExpression findName(ItemSearch condition) {
		return isItSameCondition(condition, NAME) && isNotNullData(condition) ?
				item.name.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 포함하는 문자를 가진 저자식의 조건식을 반환
	 * @param condition 저자명의 검색 조건
	 * @return 값이 있으면 조건을, 값이 없으면 null 반환
	 */
	private BooleanExpression findAuthor(ItemSearch condition) {
		return isItSameCondition(condition, AUTHOR) && isNotNullData(condition) ?
				item.author.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 포함하는 문자를 가진 출판사명의 조건식을 반환
	 * @param condition 출판사명의 검색 조건
	 * @return 값이 있으면 조건을, 값이 없으면 null 반환
	 */
	private BooleanExpression findPublisher(ItemSearch condition) {
		return isItSameCondition(condition, PUBLISHER) && isNotNullData(condition) ?
				item.publisher.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * ItemSearch안의 ItemSearchCondition의 데이터가 Null 값인지 확인
	 * @param condition 검색 조건
	 * @return 데이터가 있으면 true, 없으면 false
	 */
	private boolean isNotNullData(ItemSearch condition) {
		return condition.getData() != null;
	}

	/**
	 * ItemSearch안의 ItemSearchCondition의 데이터가 ItemSearchConditon과 같은 값인지 확인
	 * @param condition 검색 조건
	 * @param cond 비교군
	 * @return 데이터가 일치하면 true, 없으면 false
	 */
	private boolean isItSameCondition(ItemSearch condition, ItemSearchCondition cond) {
		return condition.getCondition().equals(cond);
	}
}
