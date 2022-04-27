package com.ala24.bookstore.repository.member;

import com.ala24.bookstore.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 스프링 데이터 JPA를 활용하여 검색을 할 수 있게만든 회원 저장소
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberSearchRepository{

	@Query("select m from Member m where m.loginId = :loginId")
	Optional<Member> findByLoginId(@Param("loginId") String loginId);

	@Query(value = "select m from Member m" +
			" join fetch m.cash",
			countQuery = "select count(m) from Member m")
	Page<Member> findAllFetch(Pageable pageable);
}
