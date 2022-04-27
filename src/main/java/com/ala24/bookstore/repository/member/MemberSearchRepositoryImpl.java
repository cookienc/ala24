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
import static com.ala24.bookstore.domain.member.QMember.member;
import static com.ala24.bookstore.domain.member.condition.MemberSearchCondition.*;
import static com.ala24.bookstore.domain.type.MemberStatus.*;

/**
 * 회원 검색 저장소를 구현한 저장소
 */
@Slf4j
public class MemberSearchRepositoryImpl implements MemberSearchRepository{

	private final JPAQueryFactory queryFactory;

	public MemberSearchRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	/**
	 * 검색된 회원을 페이지 형태로 반환
	 * @param condition 검색 조건
	 * @param pageable 페이징 조건
	 * @return Page<Member>
	 */
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

	/**
	 * condition의 data가 포함하는 문자를 가진 이름의 조건을 반환
	 * @param condition 검색 조건
	 * @return data를 포함하는 이름의 조건식을 반환
	 */
	private BooleanExpression findName(MemberSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, NAME) ?
				member.name.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 포함하는 문자를 가진 로그인 아이디의 조건식을 반환
	 * @param condition 검색 조건
	 * @return data를 포함하는 로그인 아이디의 조건식을 반환
	 */
	private BooleanExpression findLoginId(MemberSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, LOGIN_ID) ?
				member.loginId.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 포함하는 문자를 가진 도시의 조건식을 반환
	 * @param condition 검색 조건
	 * @return data를 포함하는 도시의 조건식을 반환
	 */
	private BooleanExpression findCity(MemberSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, CITY) ?
				member.address.city.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 포함하는 문자를 가진 주소의 조건식을 반환
	 * @param condition 검색 조건
	 * @return data를 포함하는 주소의 조건식을 반환
	 */
	private BooleanExpression findAddress(MemberSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, ADDRESS) ?
				member.address.specificAddress.containsIgnoreCase(condition.getData()) : null;
	}

	/**
	 * condition의 data가 일치하는 우편번호를 조건식을 반환
	 * @param condition 검색 조건
	 * @return data와 일치하는 우편번호의 조건식을 반환
	 */
	private BooleanExpression findZipcode(MemberSearch condition) {
		return isNotNullData(condition) && isItSameCondition(condition, ZIPCODE) ?
				member.address.zipcode.eq(Integer.parseInt(condition.getData())) : null;
	}

	/**
	 * condition의 data가 일치하는 접근권한의 조건식을 반환
	 * @param condition 검색 조건
	 * @return data와 일치하는 접근권한의 조건식을 반환
	 */
	private BooleanExpression findAuthority(MemberSearch condition) {
		if (!isNotNullData(condition) || !isItSameCondition(condition, AUTHORITY)) {
			return null;
		}

		MemberStatus status = changeToAuthority(condition.getData());
		if (status == null) {
			return null;
		}

		return member.authority.eq(status);
	}

	/**
	 * data의 값을 MemberStatus로 변환
	 * @param data memberStatus 중 한 개
	 * @return 일치하는 memberStatus
	 */
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

	/**
	 * MemberSearch안의 MemberSearchCondition의 데이터가 MemberSearchConditon과 같은 값인지 확인
	 * @param condition 검색 조건
	 * @param cond 비교군
	 * @return 데이터가 일치하면 true, 없으면 false
	 */
	private boolean isItSameCondition(MemberSearch condition, MemberSearchCondition cond) {
		return condition.getCondition().equals(cond);
	}

	/**
	 * MemberSearch안의 MemberSearchCondition의 데이터가 Null 값인지 확인
	 * @param condition 검색 조건
	 * @return 데이터가 있으면 true, 없으면 false
	 */
	private boolean isNotNullData(MemberSearch condition) {
		return condition.getData() != null;
	}
}
