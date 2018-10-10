package com.anglo.controleescolar.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anglo.controleescolar.config.object.AuthenticationService;
import com.anglo.controleescolar.config.object.User;
import com.anglo.controleescolar.notification.NotificationServiceImpl;

@Controller
@RequestMapping(value = {"app/controleescolar/authorized/authenticationController"})
public class AuthenticationController {
	@Autowired
	@Qualifier("AuthenticationService")
	private AuthenticationService authenticationService;
	@Autowired
	@Qualifier("NotificationServiceImpl")
	private NotificationServiceImpl notificationService;
	
	@RequestMapping("executeLogout")
	public String logout() {
		return "redirect:/logout";
	}
	
	@RequestMapping(value = "showAuthenticationView")
	public String AuthenticationView(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = auth != null ? authenticationService.getEnabledUser(auth.getName(), false) : null;		
		if (user != null) {
			return "redirect:/app/controleescolar/authorized/authenticationController/redirect";
		}
		return "authentication/authentication-view";
	}
	
	@RequestMapping(value = "/redirect")
	public String redirect(Model model) {
		String url = String.format("redirect:%s", getMainUrlByRole());
		return url;
	}
	
	private String getMainUrlByRole() {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		if (!authorities.isEmpty()) {
			return "/";
		}
		return "/logout";
	}
	
	@RequestMapping(value = "/accessDenied")
	public String accessDenied(Model model) {
		return "authentication/authentication-view";
	}
}
