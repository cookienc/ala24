package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.exception.HaveNotItemException;
import com.ala24.bookstore.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public Long saveItem(Item item) {
		return itemRepository.save(item).getId();
	}

	public Item findOne(Long itemId) {
		return itemRepository.findById(itemId)
				.orElseThrow(() -> new HaveNotItemException("해당 아이템은 존재하지 않습니다."));
	}

	public List<Item> findAll() {
		return itemRepository.findAll();
	}

}
