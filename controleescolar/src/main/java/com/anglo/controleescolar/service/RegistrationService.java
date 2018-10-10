package com.anglo.controleescolar.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.anglo.controleescolar.repository.PagingAndSortingExtendedRepository;
import com.anglo.controleescolar.repository.specification.EntitySpecification;

public abstract class RegistrationService<T, ID, SearchCriteria extends EntitySpecification<T, ID>, Repository extends PagingAndSortingExtendedRepository<T, ID>> {
	protected abstract Validator getCanSaveValidator();
	protected abstract Validator getCanDeleteValidator();
	protected abstract Repository getRepository();
	protected abstract SearchCriteria getSpecificationInstance();
	public abstract T prepareForRegistration(ID id);
	
	public Page<T> getItems(T criteria, Pageable page) {
		if (page == null) {
			page = PageRequest.of(0, 30);
		}
		SearchCriteria specification = getSpecificationInstance();
		specification.setCriteria(criteria);
		return getRepository().findAll(specification, page);
	}
	
	@Transactional
	public boolean canSave(T entity, Errors errors) {
		getCanSaveValidator().validate(entity, errors);
		return !errors.hasErrors();
	}
	
	@Transactional
	public T save(T entity, Errors errors) {
		getCanSaveValidator().validate(entity, errors);
		if (errors.hasErrors()) {
			return entity;
		}
		T result = getRepository().save(entity);
		return result;
	}
	
	public List<T> getItems(List<ID> codes) {
		SearchCriteria specification = getSpecificationInstance();
		specification.setIds(codes);
		List<T> entities = getRepository().findAll(specification);
		return entities;
	}
	
	public T getItem(ID code) {
		List<T> items = getItems(Collections.singletonList(code));
		if (items == null || items.isEmpty()) {
			return null;
		}
		return items.get(0);
	}
	
	@Transactional
	public Errors delete(List<ID> codes) {
		List<T> entities = getItems(codes);
		Errors errors = new BeanPropertyBindingResult(entities, "entitiesToDelete");
		getCanDeleteValidator().validate(entities, errors);
		if (!errors.hasErrors()) {
			getRepository().deleteAll(entities);
		}
		return errors;
	}
	
	@Transactional
	public Errors delete(ID code) {
		return delete(Collections.singletonList(code));
	}
}
