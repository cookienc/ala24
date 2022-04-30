package com.ala24.bookstore.service;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.web.controller.search.item.ItemSearch;
import com.ala24.bookstore.web.controller.search.item.ItemSearchCondition;
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

/**
 * 상품과 관련된 함수를 가지고 있는 서비스 계층
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	/**
	 * 상품을 저장하는 함수
	 * @param item 저장할 상품
	 * @return 저장한 상품의 아이디
	 */
	@Transactional
	public Long saveItem(Item item) {
		return itemRepository.save(item).getId();
	}

	/**
	 * 상품 하나를 조회
	 * @param itemId 조회할 상품의 아이디
	 * @return 조회한 상품
	 */
	public Item findOne(Long itemId) {
		return itemRepository.findById(itemId)
				.orElseThrow(() -> new NoSuchElementException(NO_ITEM.toString()));
	}

	/**
	 * 전체 상품 조회
	 * @return 전체 상품 리스트
	 */
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	/**
	 * 상품 검색 조건을 이용하여 페이징된 상품을 조회하여 ItemListDto로 변환하여 반환
	 * @param condition 상품 검색 조건
	 * @param pageable 페이징 조건
	 * @return 상품을 페이징처리하여 ItemListDto로 반환
	 */
	public Page<ItemListDto> search(ItemSearch condition, Pageable pageable) {

		if (condition.getCondition() == null) {
			condition.setCondition(ItemSearchCondition.NORMAL);
		}

		return itemRepository.searchPage(condition, pageable)
				.map(ItemListDto::new);
	}

	/**
	 * 상품을 수정하는 함수
	 * @param itemId 수정할 상품의 아이디
	 * @param item 수정할 상품
	 */
	@Transactional
	public void updateItem(Long itemId, Item item) {
		Item findItem = findOne(itemId);

		//상품의 종류를 변경하려면 데이터를 제거하고 새로운 상품으로 넣어야 한다.
		if (doesItChangeCategory(item, findItem)) {
			itemRepository.delete(findItem);
			saveItem(item);
			return;
		}

		findItem.updateInfo(item);
	}

	/**
	 * 상품의 종류(카테고리)가 바뀌었는지 확인하는 함수
	 * @param item 업데이트할 상품
	 * @param findItem 상품의 기준
	 * @return class가 일치하면 true 아니면 false
	 */
	private boolean doesItChangeCategory(Item item, Item findItem) {
		return !findItem.getClass().equals(item.getClass());
	}
}
