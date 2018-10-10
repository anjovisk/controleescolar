package com.anglo.controleescolar.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.anglo.controleescolar.repository.entity.Teste;

public class TesteSearch implements EntitySpecification<Teste, Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1759003337875579397L;
	private Teste criteria;
	private List<Integer> codes;
	
	public TesteSearch() {
		
	}
	
	public TesteSearch(Teste teste) {
		criteria = teste;
	}
	
	public TesteSearch(Teste teste, List<Integer> codes) {
		criteria = teste;
		this.codes = codes;
	}
	
	@Override
	public void setIds(List<Integer> ids) {
		codes = ids;
	}
	
	@Override
	public void setCriteria(Teste criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Teste> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (codes != null) {
			predicates.add(root.<Integer>get("code").in(codes));
		}	
		
		if (criteria != null) {
			if (criteria.getName() != null && !criteria.getName().isEmpty()) {
				predicates.add(cb.like(cb.lower(root.<String>get("name")), String.format("%%%s%%", criteria.getName().toLowerCase().replace("_", "\\_"))));
			}
		}						
		query.distinct(true);
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
