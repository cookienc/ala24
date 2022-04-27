package com.ala24.bookstore.repository.orders;

import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.orders.condition.OrderSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 주문 검색을 할 수 있게 만든 인터페이스
 */
public interface OrderSearchRepository {

	Page<Order> searchPage(OrderSearch condition, String loginId, Pageable pageable);
}
