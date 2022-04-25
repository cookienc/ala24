package com.ala24.bookstore.repository.orders;

import com.ala24.bookstore.domain.orders.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderSearchRepository {

	@Query(value = "select o from Order o" +
			" join fetch o.member m" +
			" join fetch o.orderItems oi" +
			" join fetch oi.item" +
			" join fetch o.delivery d",
	countQuery = "select count(o) from Order o")
	Page<Order> findAllFetch(Pageable pageable);
}
