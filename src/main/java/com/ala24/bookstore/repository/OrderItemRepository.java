package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 아이템을 관리하는 리포지토리
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
