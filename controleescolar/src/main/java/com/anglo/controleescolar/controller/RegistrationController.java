package com.anglo.controleescolar.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anglo.controleescolar.repository.PagingAndSortingExtendedRepository;
import com.anglo.controleescolar.repository.entity.Entity;
import com.anglo.controleescolar.repository.specification.EntitySpecification;
import com.anglo.controleescolar.service.RegistrationService;

public abstract class RegistrationController<T extends Entity, ID, Service extends RegistrationService<T, ID, EntitySpecification<T, ID>, PagingAndSortingExtendedRepository<T, ID>>> {
	@Autowired
	protected MessageSource messageSource;
	
	protected Service registrationService;
	protected abstract String redirect(String urlSufix);
	
	RegistrationController(Service registrationService) {
		this.registrationService = registrationService;
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(name="loadMainView", required=false, defaultValue="true") String loadMainView) {
		return redirect(String.format("/paginate?loadMainView=%s", loadMainView));
	}
	
 	protected void paginate(int pageNumber, String fieldName, int asc, Model model, T parameters, BindingResult result) {
		Page<T> items = null;
		while (pageNumber > 0) {
			Order order = new Order(asc == 0 ? Direction.ASC : Direction.DESC, fieldName);
			List<Order> orders = new ArrayList<Order>();
			orders.add(order);
			Sort sort = Sort.by(orders);
			PageRequest page = PageRequest.of(pageNumber - 1, 30, sort);
			items = registrationService.getItems(parameters, page);
			if (items.getNumberOfElements() > 0) {
				break;
			}
			pageNumber--;
		}
		int current = items.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(current + 10, items.getTotalPages());
	    model.addAttribute("items", items);
	    model.addAttribute("parameters", parameters);
		model.addAttribute("orderBy", fieldName);
		model.addAttribute("orderByAsc", asc);
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		if (result.hasErrors()) {
			String errorMessage = "";
			for (FieldError error : result.getFieldErrors()) {
				for (int i = 0; i < error.getCodes().length - 1; i++) {
					String code = error.getCodes()[i];
					String[] args = { String.valueOf(error.getRejectedValue()) };
					String message = messageSource.getMessage(code, args, Locale.getDefault());
					if (message != null && !message.isEmpty()) {
						errorMessage += message + "\\n";
						break;
					}
				}
			}
			model.addAttribute("errorMessage", errorMessage);
		}
	}
 	
 	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveObject(@ModelAttribute("entityForm") T entityForm, BindingResult result,
			@RequestParam(name = "validationOnly", required=false, defaultValue="false") boolean validationOnly,
			Model model) {
		if (validationOnly) {
			registrationService.canSave(entityForm, result);
		} else {
			registrationService.save(entityForm, result);
		}
		Map<String, Object> map = new HashMap<>();
		if (result.hasErrors()) {
			map.put("errors", result.getAllErrors());
		}
		map.put("entityForm", entityForm);
		return map;
	}
 	
 	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("ids") List<ID> ids) {
		Map<String, Object> map = new HashMap<>();
		Errors errors = registrationService.delete(ids);
		if(errors.hasErrors()) {
			map.put("errors", errors.getAllErrors());
			map.put("success", false);
		}
		map.put("success", true);
		return map;
	}
}
