package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("select m from Member m where m.loginId = :loginId")
	Optional<Member> findByLoginId(@Param("loginId") String loginId);

	@Query("select m from Member m " +
			"join fetch m.cash c")
	List<Member> findAllWithCash();
}
