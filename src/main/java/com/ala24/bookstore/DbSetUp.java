package com.ala24.bookstore;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.item.Novel;
import com.ala24.bookstore.domain.item.Poem;
import com.ala24.bookstore.domain.item.SelfDevelopment;
import com.ala24.bookstore.domain.type.MemberStatus;
import com.ala24.bookstore.service.ItemService;
import com.ala24.bookstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DbSetUp {

	private final MemberService memberService;
	private final ItemService itemService;
	private final DataBaseCleanup dataBaseCleanup;

	@PostConstruct
	public void setUp() {
		memberSetUp();
		itemSetUp();
	}

	@PreDestroy
	public void tearDown() {
		dataBaseCleanup.execute();
	}

	private void memberSetUp() {
		Member test = Member.builder()
				.name("test")
				.loginId("test")
				.password("test")
				.cash(Cash.charge(100000L))
				.address(Address.builder()
						.zipcode(12345)
						.city("Seoul")
						.specificAddress("Apartment")
						.build())
				.build();
		memberService.join(test);

		Member admin = Member.builder()
				.name("admin")
				.loginId("admin")
				.password("admin")
				.address(Address.builder()
						.zipcode(12345)
						.city("admin")
						.specificAddress("admin")
						.build())
				.build();
		admin.changeAuthority(MemberStatus.ADMIN);

		memberService.join(admin);
	}

	private void itemSetUp() {
		Novel novel1 = Novel.builder()
				.name("기사단장죽이기1")
				.author("무라카미 하루키")
				.publisher("문학동네")
				.price(14670)
				.stockQuantity(800)
				.build();
		Novel novel2 = Novel.builder()
				.name("기사단장죽이기2")
				.author("무라카미 하루키")
				.publisher("문학동네")
				.price(14670)
				.stockQuantity(1000)
				.build();

		Poem poem1 = Poem.builder()
				.name("꽃을 보듯 너를 본다")
				.author("나태주")
				.publisher("지혜")
				.price(9000)
				.stockQuantity(500)
				.build();
		Poem poem2 = Poem.builder()
				.name("윤동주 전 시집 하늘과 바람과 별과 시")
				.author("윤동주")
				.publisher("스타북스")
				.price(11700)
				.stockQuantity(1500)
				.build();

		SelfDevelopment sd1 = SelfDevelopment.builder()
				.name("자바 ORM 표준 JPA 프로그래밍")
				.author("김영한")
				.publisher("에이콘출판사")
				.price(38700)
				.stockQuantity(5000)
				.build();

		SelfDevelopment sd2 = SelfDevelopment.builder()
				.name("토비의 스프링 3.1 Vol. 1 스프링의 이해와 원리")
				.author("이일민")
				.publisher("에이콘출판사")
				.price(3600)
				.stockQuantity(500)
				.build();

		SelfDevelopment sd3 = SelfDevelopment.builder()
				.name("토비의 스프링 3.1 Vol. 2 스프링의 기술과 선택")
				.author("이일민")
				.publisher("에이콘출판사")
				.price(3600)
				.stockQuantity(500)
				.build();

		itemService.saveItem(novel1);
		itemService.saveItem(novel2);
		itemService.saveItem(poem1);
		itemService.saveItem(poem2);
		itemService.saveItem(sd1);
		itemService.saveItem(sd2);
		itemService.saveItem(sd3);
	}

}
