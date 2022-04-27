package com.ala24.bookstore.repository.item;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.condition.ItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 검색을 할 수 있게 만든 저장소
 */
public interface ItemSearchRepository {

	Page<Item> searchPage(ItemSearch itemSearch, Pageable pageable);
}
