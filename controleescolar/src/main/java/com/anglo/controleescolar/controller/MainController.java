package com.anglo.controleescolar.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anglo.controleescolar.notification.email.EmailParameters;
import com.anglo.controleescolar.report.ReportManager;
import com.anglo.controleescolar.repository.entity.Teste;
import com.anglo.controleescolar.service.TesteRegistrationService;

@Controller
@RequestMapping(value = {"/", "app/controleescolar/authorized/mainController"})
public class MainController {
	@Autowired
	private ReportManager reportManager;
	@Autowired
	private TesteRegistrationService testRegistrationService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/showTestView")
	public String showSignHelperMozillaView() {
		return "controleescolar/index";
	}
	
	@RequestMapping(value = "/")
	public String showIndexView() {
		return "controleescolar/index1";
	}
	
	@RequestMapping(value = "/showrArchitectureTestReport")
	public void showrArchitectureTestReport(Model model, HttpServletResponse response) throws IOException {
		String fileName = "architecture-test.pdf";
		String mimeType= URLConnection.guessContentTypeFromName(fileName);
		if(mimeType == null){
			mimeType = "application/octet-stream";
		}
        response.setContentType(mimeType);
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8")
			.replaceAll("\\+", "%20")
	        .replaceAll("\\%21", "!")
	        .replaceAll("\\%27", "'")
	        .replaceAll("\\%28", "(")
	        .replaceAll("\\%29", ")")
	        .replaceAll("\\%7E", "~");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        Object[] items = new Object[2];
        for (int index = 0; index < 2; index++) {
        	EmailParameters item = new EmailParameters();
        	item.setUsername("Login " + index);
        	item.setPort(index);
        	items[index] = item;
        }
        byte[] file = reportManager.exportArchitectureTestReport(items);
        response.setContentLength(file.length);
		InputStream inputStream = new ByteArrayInputStream(file);
        FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	@RequestMapping(value = "/insertTest")
	@ResponseBody
	public String insertTest(@RequestParam(required=false) String name) {
		Teste teste = new Teste();
		teste.setName(name);
		Errors errors = new BeanPropertyBindingResult(teste, "teste");
		testRegistrationService.save(teste, errors);
		if (errors.hasErrors()) {
			String errorMessage = "";
			errors.getAllErrors();
			for (ObjectError error : errors.getAllErrors()) {
				errorMessage += (error.getDefaultMessage() == null ? messageSource.getMessage(error.getCode(), null, null) : error.getDefaultMessage()) + "\r\n";
			}
			return errorMessage;
		}
		return String.format("{code: '%s', name: '%s'}", teste.getCode(), teste.getName());
	}
	
	@RequestMapping(value = "/deleteTest")
	@ResponseBody
	public String insertTest(@RequestParam(required=false) List<Integer> codes) {
		Errors errors = testRegistrationService.delete(codes);
		if (errors.hasErrors()) {
			String errorMessage = "";
			errors.getAllErrors();
			for (ObjectError error : errors.getAllErrors()) {
				errorMessage += (error.getDefaultMessage() == null ? messageSource.getMessage(error.getCode(), null, null) : error.getDefaultMessage()) + "\r\n";
			}
			return errorMessage;
		}
		return "Testes removidos com sucesso.";
	}
}
