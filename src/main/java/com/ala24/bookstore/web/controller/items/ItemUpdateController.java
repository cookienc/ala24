package com.ala24.bookstore.web.controller.items;

import com.ala24.bookstore.domain.item.Item;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.web.dtos.itemdto.ItemUpdateFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 상품의 업데이트를 담당하는 컨트롤러
 * ItemCategoryController를 사용하여 카테고리를 뷰에서 이용할 수 있게 함
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemUpdateController implements ItemCategoryController{

	private final ItemService itemService;

	/**
	 * 상품 업데이트 폼으로 이동하는 함수
	 * @param itemId 업데이트할 상품의 아이디
	 * @param model 모델
	 * @return view 경로
	 */
	@GetMapping("/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Item findItem = itemService.findOne(itemId);

		ItemUpdateFormDto updateForm = new ItemUpdateFormDto();
		model.addAttribute("updateForm", updateForm.setInfo(findItem));
		return "items/updateForm";
	}

	/**
	 * 상품 업데이트를 담당하는 함수
	 * @param itemId 업데이트할 상품의 아이디
	 * @param updateForm 폼에서 입력된 내용
	 * @param result 오류를 저장할 result
	 * @param redirectAttributes 반환시 uri에 자원을 표시하지 않게 하기 위함
	 * @return 성공하면 상품 리스트의 view 경로, 아니면 updateForm의 경로
	 */
	@PostMapping("/{itemId}/edit")
	public String update(@PathVariable("itemId") Long itemId, @Valid @ModelAttribute("updateForm") ItemUpdateFormDto updateForm,
						 BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			log.info("ItemUpdateForm 검증 오류 : {}", result);
			return "items/updateForm";
		}

		itemService.updateItem(itemId, updateForm.toEntity());
		return "redirect:/items/list";
	}
}
