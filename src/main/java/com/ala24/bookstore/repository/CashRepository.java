package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<Cash, Long> {
}
