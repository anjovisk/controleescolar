package com.anglo.controleescolar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface PagingAndSortingExtendedRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
	List<T> findAll(Specification<T> search);
	Page<T> findAll(Specification<T> search, Pageable page);
}
