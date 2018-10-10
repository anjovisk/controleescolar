package com.anglo.controleescolar.controller.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.anglo.controleescolar.repository.entity.Teste;

@Component(value="CanDeleteTestValidator")
public class CanDeleteTestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(List.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		List<?> list = (List<?>)target;
		if ((list == null) || (list.isEmpty())) {
			errors.reject("validatin.the.list.cannot.be.empty");
		} else {
			List<Teste> testes = new ArrayList<>();
			for (Object object : list) {
				if (object instanceof Teste) {
					testes.add((Teste)object);
				}
			}
			if ((testes == null) || (testes.isEmpty())) {
				errors.reject("validatin.the.list.cannot.be.empty");
			}
		}
	}

}
