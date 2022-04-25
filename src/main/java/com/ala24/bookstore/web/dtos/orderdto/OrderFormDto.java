package com.ala24.bookstore.web.dtos.orderdto;

import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class OrderFormDto {

	public static final int NORMAL_QUANTITY = 1;
	@NotNull
	private Long itemId;

	@NotBlank
	private String itemName;

	@NotNull
	private Integer price;

	@NotNull
	private Integer stockQuantity;

	@NotNull
	@Range(min = 1)
	private Integer quantity;

	@NotNull
	private Long memberId;

	@NotBlank
	private String memberName;

	@NotBlank
	private String city;

	@NotBlank
	private String specificAddress;

	@NotNull
	private Integer zipcode;

	@Builder
	public OrderFormDto(Long itemId, String itemName, Integer stockQuantity, Integer price, Integer quantity,
						Long memberId, String memberName, String city,
						String specificAddress, Integer zipcode) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.stockQuantity = stockQuantity;
		this.price = price;
		this.quantity = quantity;
		this.memberId = memberId;
		this.memberName = memberName;
		this.city = city;
		this.specificAddress = specificAddress;
		this.zipcode = zipcode;
	}

	public OrderFormDto toDto(Item item, Member member) {
		return OrderFormDto.builder()
				.itemId(item.getId())
				.itemName(item.getName())
				.stockQuantity(item.getStockQuantity())
				.price(item.getPrice())
				.quantity(NORMAL_QUANTITY)
				.memberId(member.getId())
				.memberName(member.getName())
				.city(member.getAddress().getCity())
				.specificAddress(member.getAddress().getSpecificAddress())
				.zipcode(member.getAddress().getZipcode())
				.build();
	}
}
