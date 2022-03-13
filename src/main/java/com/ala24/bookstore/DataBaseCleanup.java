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

@Service
public class DataBaseCleanup implements InitializingBean {

	@PersistenceContext
	private EntityManager entityManager;

	private List<String> tableNames;

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

	@Transactional
	public void execute() {
		entityManager.flush();
		entityManager.clear();

		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

		for (String tableName : tableNames) {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
		}
		entityManager.createNativeQuery("TRUNCATE  TABLE orders");
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}
}
