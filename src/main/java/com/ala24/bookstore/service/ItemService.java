package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ala24.bookstore.exception.utils.Sentence.NO_ITEM;

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
				.orElseThrow(() -> new NoSuchElementException(NO_ITEM.toString()));
	}

	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Transactional
	public void updateItem(Long itemId, Item item) {
		Item findItem = findOne(itemId);
		System.out.println("findItem.getClass() = " + findItem.getClass());
		System.out.println("Item.getClass() = " + item.getClass());
		if (doesItChangeCategory(item, findItem)) {
			itemRepository.delete(findItem);
			saveItem(item);
			return;
		}

		findItem.updateInfo(item);
	}

	private boolean doesItChangeCategory(Item item, Item findItem) {
		return !findItem.getClass().equals(item.getClass());
	}
}
