package com.ala24.bookstore.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Poem extends Item{

	@Builder
	private Poem(String name, String author, String publisher, int price, int stockQuantity) {
		super(name, author, publisher, price, stockQuantity);
	}
}
