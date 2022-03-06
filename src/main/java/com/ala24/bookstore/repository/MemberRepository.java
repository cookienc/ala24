package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("select m from Member m where m.loginId = :loginId")
	Member findByLoginId(@Param("loginId") String loginId);
}
