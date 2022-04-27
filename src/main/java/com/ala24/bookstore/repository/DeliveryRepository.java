package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 배송을 저장하는 저장소
 */
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
