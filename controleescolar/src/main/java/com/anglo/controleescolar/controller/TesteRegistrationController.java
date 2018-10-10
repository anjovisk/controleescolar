package com.anglo.controleescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anglo.controleescolar.repository.entity.Teste;
import com.anglo.controleescolar.service.TesteRegistrationService;

@Controller
@RequestMapping(value="/app/controleescolar/area/teste/testeRegistrationController")
public class TesteRegistrationController extends RegistrationController<Teste, Integer, TesteRegistrationService> {
	@Autowired
	public TesteRegistrationController(TesteRegistrationService testeRegistrationService) {
		super(testeRegistrationService);
	}
	
	protected String redirect(String urlSufix) {
		return String.format("redirect:/app/controleescolar/area/teste/testeRegistrationController%s", urlSufix);
	}

	@RequestMapping(value = { "/paginate" })
 	public String paginate(@RequestParam(name="pageNumber", required=false, defaultValue="1") int pageNumber,
 			@RequestParam(name="fieldName", required=false, defaultValue="name") String fieldName,
 			@RequestParam(name="asc", required=false, defaultValue="0") Integer asc, 
 			@RequestParam(name="loadMainView", required=false, defaultValue="true") boolean loadMainView, 			
 			Model model, @ModelAttribute Teste parameters, BindingResult result) {
		super.paginate(pageNumber, fieldName, asc, model, parameters, result);
		if (loadMainView) {
	    	return "controleescolar/teste/teste-registration-main-view";
	    } else {
	    	return "controleescolar/teste/teste-registration-list-view";
	    }
	}
	
	@RequestMapping("/edit")
	public String edit(@RequestParam(required=false, name="id", defaultValue="0") int id, Model model) {
		model.addAttribute("entityForm", registrationService.prepareForRegistration(id));
		return "controleescolar/teste/teste-registration-form-view";
	}
}
