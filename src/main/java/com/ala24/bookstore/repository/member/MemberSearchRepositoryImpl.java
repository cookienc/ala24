package com.ala24.bookstore.repository.member;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.member.condition.MemberSearch;
import com.ala24.bookstore.domain.member.condition.MemberSearchCondition;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ala24.bookstore.domain.QCash.cash;
import static com.ala24.bookstore.domain.QMember.member;
import static com.ala24.bookstore.domain.member.condition.MemberSearchCondition.*;
import static com.ala24.bookstore.domain.type.MemberStatus.*;

@Slf4j
public class MemberSearchRepositoryImpl implements MemberSearchRepository{

	private final JPAQueryFactory queryFactory;

	public MemberSearchRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Member> searchPage(MemberSearch condition, Pageable pageable) {
		QueryResults<Member> results = queryFactory.selectFrom(member)
				.join(member.cash, cash).fetchJoin()
				.where(findName(condition), findLoginId(condition), findCity(condition),
						findZipcode(condition), findAddress(condition), findAuthority(condition))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		List<Member> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression findName(MemberSearch condition) {
		return isNoNullData(condition) && isItSameCondition(condition, NAME) ?
				member.name.containsIgnoreCase(condition.getData()) : null;
	}

	private BooleanExpression findLoginId(MemberSearch condition) {
		return isNoNullData(condition) && isItSameCondition(condition, LOGIN_ID) ?
				member.loginId.containsIgnoreCase(condition.getData()) : null;
	}

	private BooleanExpression findCity(MemberSearch condition) {
		return isNoNullData(condition) && isItSameCondition(condition, CITY) ?
				member.address.city.containsIgnoreCase(condition.getData()) : null;
	}

	private BooleanExpression findAddress(MemberSearch condition) {
		return isNoNullData(condition) && isItSameCondition(condition, ADDRESS) ?
				member.address.specificAddress.containsIgnoreCase(condition.getData()) : null;
	}

	private BooleanExpression findZipcode(MemberSearch condition) {
		return isNoNullData(condition) && isItSameCondition(condition, ZIPCODE) ?
				member.address.zipcode.eq(Integer.parseInt(condition.getData())) : null;
	}

	private BooleanExpression findAuthority(MemberSearch condition) {
		if (!isNoNullData(condition) || !isItSameCondition(condition, AUTHORITY)) {
			return null;
		}

		MemberStatus status = changeToAuthority(condition.getData());
		if (status == null) {
			return null;
		}

		return member.authority.eq(status);
	}

	private MemberStatus changeToAuthority(String data) {
		switch (data.toUpperCase()) {
			case "ADMIN":
				return ADMIN;
			case "USER":
				return USER;
			case "GUEST":
				return GUEST;
		}
		return null;
	}

	private boolean isItSameCondition(MemberSearch condition, MemberSearchCondition cond) {
		return condition.getCondition().equals(cond);
	}

	private boolean isNoNullData(MemberSearch condition) {
		return condition.getData() != null;
	}
}
