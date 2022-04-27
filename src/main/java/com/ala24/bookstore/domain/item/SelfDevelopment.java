package com.ala24.bookstore.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * 아이템 속성을 상속받는 SelfDevelopment(자기계발서) 클래스
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelfDevelopment extends Item{

	@Builder
	private SelfDevelopment(Long id, String name, String author, String publisher, int price, int stockQuantity) {
		super(id, name, author, publisher, price, stockQuantity);
	}
}
