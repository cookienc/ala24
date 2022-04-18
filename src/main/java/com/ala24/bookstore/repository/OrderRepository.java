package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select o from Order o" +
			" join fetch o.member m" +
			" join fetch o.orderItems oi" +
			" join fetch o.delivery d")
	List<Order> findAllEntity();
}
