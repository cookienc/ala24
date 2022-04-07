package com.ala24.bookstore.web.dtos.orderdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderServiceDto {

	private Long itemId;
	private Long memberId;
	private Integer itemQuantity;

	@Builder
	public OrderServiceDto(Long itemId, Long memberId, Integer itemQuantity) {
		this.itemId = itemId;
		this.memberId = memberId;
		this.itemQuantity = itemQuantity;
	}


	public OrderServiceDto toDto(OrderFormDto orderForm) {
		return OrderServiceDto.builder()
				.itemId(orderForm.getItemId())
				.memberId(orderForm.getMemberId())
				.itemQuantity(orderForm.getQuantity())
				.build();
	}
}
