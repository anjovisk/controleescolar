package com.anglo.controleescolar.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.anglo.controleescolar.util.Contact;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		e.printStackTrace(printStream);
		String stackTrace = new String(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
		ModelAndView model = new ModelAndView();
		model.addObject("exception", e);
		model.addObject("stackTrace", stackTrace);
		model.addObject("errorForm", new Contact());
		model.addObject("url", req.getRequestURL());
    	model.setViewName("common/error-view");
    	Logger.getLogger (Exception.class.getName()).log(Level.WARNING, e.getMessage(), e);
		e.printStackTrace();
    	return model;
	}
	
	@ExceptionHandler(value = UnavailableLinkException.class)
	public ModelAndView unavailableLinkErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		Logger.getLogger (UnavailableLinkException.class.getName()).log(Level.WARNING, e.getMessage(), e);
		e.printStackTrace();
		ModelAndView model = new ModelAndView();
		model.addObject("exception", e);
		model.addObject("url", req.getRequestURL());
    	model.setViewName("common/unavailable-link-view");
    	return model;
	}
}
