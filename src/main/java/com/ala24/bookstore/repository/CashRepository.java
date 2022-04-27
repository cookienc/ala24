package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원의 계좌를 관리하는 저장소
 */
public interface CashRepository extends JpaRepository<Cash, Long> {
}
