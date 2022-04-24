package com.ala24.bookstore.repository.item;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.repository.condition.ItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemSearchRepository {

	Page<Item> searchPage(ItemSearch itemSearch, Pageable pageable);
}
