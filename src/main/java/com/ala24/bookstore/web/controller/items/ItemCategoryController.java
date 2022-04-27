package com.ala24.bookstore.web.controller.items;

import com.ala24.bookstore.web.dtos.itemdto.ItemCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 아이템의 카테고리를 나타내는 컨트롤러
 */
@Controller
public interface ItemCategoryController {

	/**
	 * 아이템의 카테고리를 반환하는 함수
	 * @return 아이템 카테고리를 반환
	 */
	@ModelAttribute("itemCategories")
	default ItemCategory[] itemCategories() {
		return ItemCategory.values();
	}
}
