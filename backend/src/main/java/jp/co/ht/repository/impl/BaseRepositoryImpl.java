package jp.co.ht.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class BaseRepositoryImpl {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	protected <T> Page<T> createPageable(List<T> data, int page, int size, long total){
		Pageable pageable = PageRequest.of(page, size);
		return new PageImpl<T>(data, pageable, total);
	}
}
