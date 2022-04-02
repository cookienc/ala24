package com.ala24.bookstore.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Novel extends Item{

	@Builder
	private Novel(Long id, String name, String author, String publisher, int price, int stockQuantity) {
		super(id, name, author, publisher, price, stockQuantity);
	}
}
