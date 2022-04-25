package com.ala24.bookstore.repository.member;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.member.condition.MemberSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberSearchRepository {

	Page<Member> searchPage(MemberSearch memberSearch, Pageable pageable);
}
