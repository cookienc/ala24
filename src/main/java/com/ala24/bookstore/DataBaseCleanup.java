package com.ala24.bookstore;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 데이터 베이스를 초기화하는 테스트용 클래스
 */
@Service
public class DataBaseCleanup implements InitializingBean {

	@PersistenceContext
	private EntityManager entityManager;

	private List<String> tableNames;

	/**
	 * 테이블의 이름을 가져오는 함수
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		tableNames = entityManager.getMetamodel().getEntities().stream()
				.filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
				.map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
				.collect(Collectors.toList());

		tableNames.remove("self_development");
		tableNames.remove("book");
		tableNames.remove("poem");
		tableNames.remove("order");
	}

	/**
	 * 테이블을 초기화하는 함수
	 */
	@Transactional
	public void execute() {
		entityManager.flush();
		entityManager.clear();

		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

		for (String tableName : tableNames) {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
		}
		entityManager.createNativeQuery("TRUNCATE TABLE orders").executeUpdate();
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}
}
