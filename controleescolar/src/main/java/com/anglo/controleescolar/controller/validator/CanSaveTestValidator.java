package com.anglo.controleescolar.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.anglo.controleescolar.repository.entity.Teste;

@Component(value="CanSaveTestValidator")
public class CanSaveTestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Teste.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Teste teste = (Teste)target;
		if (teste == null) {
			errors.reject("validatin.the.entity.must.be.informed");
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.mandatory.field");
		}
	}

}
