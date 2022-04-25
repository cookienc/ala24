package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.domain.item.condition.ItemSearch;
import com.ala24.bookstore.domain.item.condition.ItemSearchCondition;
import com.ala24.bookstore.repository.item.ItemRepository;
import com.ala24.bookstore.web.dtos.itemdto.ItemListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<ItemListDto> search(ItemSearch condition, Pageable pageable) {

		if (condition.getCondition() == null) {
			condition.setCondition(ItemSearchCondition.NORMAL);
		}

		return itemRepository.searchPage(condition, pageable)
				.map(ItemListDto::new);
	}

	@Transactional
	public void updateItem(Long itemId, Item item) {
		Item findItem = findOne(itemId);
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

	public Page<ItemListDto> findAll(Pageable pageable) {
		return itemRepository.findAll(pageable)
				.map(ItemListDto::new);
	}
}
