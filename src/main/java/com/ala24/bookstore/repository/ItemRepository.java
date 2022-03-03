package com.ala24.bookstore.repository;

import com.ala24.bookstore.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
