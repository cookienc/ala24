package com.ala24.bookstore.repository.member;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.web.controller.search.member.MemberSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 회원 검색이 가능하게 만든 저장소
 */
public interface MemberSearchRepository {

	Page<Member> searchPage(MemberSearch memberSearch, Pageable pageable);
}
