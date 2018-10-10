package com.anglo.controleescolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import com.anglo.controleescolar.repository.PagingAndSortingExtendedRepository;
import com.anglo.controleescolar.repository.TesteRepository;
import com.anglo.controleescolar.repository.entity.Teste;
import com.anglo.controleescolar.repository.specification.EntitySpecification;
import com.anglo.controleescolar.repository.specification.TesteSearch;

@Service
public class TesteRegistrationService extends RegistrationService<Teste, Integer, EntitySpecification<Teste, Integer>, PagingAndSortingExtendedRepository<Teste, Integer>> {
	@Autowired
	private TesteRepository testeRepository;
	@Autowired
	@Qualifier("CanDeleteTestValidator")
	private Validator canDeleteTestValidator;
	@Autowired
	@Qualifier("CanSaveTestValidator")
	private Validator canSaveTestValidator;
	
	@Override
	protected Validator getCanSaveValidator() {
		return canSaveTestValidator;
	}

	@Override
	protected Validator getCanDeleteValidator() {
		return canDeleteTestValidator;
	}

	@Override
	protected TesteRepository getRepository() {
		return testeRepository;
	}

	@Override
	protected TesteSearch getSpecificationInstance() {
		return new TesteSearch();
	}

	@Override
	public Teste prepareForRegistration(Integer id) {
		Teste result = getItem(id);
		if (result == null) {
			result = new Teste();
		}
		return result;
	}
}
