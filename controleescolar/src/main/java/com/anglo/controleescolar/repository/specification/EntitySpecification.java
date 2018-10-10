package com.anglo.controleescolar.repository.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public interface EntitySpecification<T, ID> extends Specification<T> {
	void setIds(List<ID> ids);
	void setCriteria(T criteria);
}
