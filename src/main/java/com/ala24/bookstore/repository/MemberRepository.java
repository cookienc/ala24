package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
