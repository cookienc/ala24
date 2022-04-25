package com.ala24.bookstore.repository.member;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.member.condition.MemberSearch;
import com.ala24.bookstore.domain.member.condition.MemberSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ala24.bookstore.domain.QCash.cash;
import static com.ala24.bookstore.domain.QMember.member;
import static com.ala24.bookstore.domain.member.condition.MemberSearchCondition.NAME;

public class MemberSearchRepositoryImpl implements MemberSearchRepository{

	private final JPAQueryFactory queryFactory;

	public MemberSearchRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Member> searchPage(MemberSearch condition, Pageable pageable) {
		QueryResults<Member> results = queryFactory.selectFrom(member)
				.join(member.cash, cash).fetchJoin()
				.where(findName(condition))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		List<Member> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression findName(MemberSearch condition) {
		return isNotData(condition) && isItSameCondition(condition, NAME) ?
				member.name.containsIgnoreCase(condition.getData()) : null;
	}

	private boolean isItSameCondition(MemberSearch condition, MemberSearchCondition cond) {
		return condition.getCondition().equals(cond);
	}

	private boolean isNotData(MemberSearch condition) {
		return condition.getData() != null;
	}
}
