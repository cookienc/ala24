package com.ala24.bookstore.repository.item;

import com.ala24.bookstore.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 스프링 데이터 JPA를 활용하여 검색을 할 수 있게 만든 상품 저장소
 */
public interface ItemRepository extends JpaRepository<Item, Long>, ItemSearchRepository {

	Page<Item> findAll(Pageable pageable);
}
