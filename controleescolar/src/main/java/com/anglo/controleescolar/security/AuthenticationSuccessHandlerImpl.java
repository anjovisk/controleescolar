package com.anglo.controleescolar.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.anglo.controleescolar.config.object.LoggedUser;
import com.anglo.controleescolar.config.object.User;
import com.anglo.controleescolar.logging.SystemLogAction;
import com.anglo.controleescolar.logging.SystemLogRegistration;

@Component
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {		
		User user = ((LoggedUser)auth.getPrincipal()).getUser();
        updateUserAfterLogin(user);
        super.onAuthenticationSuccess(request, response, auth);
        SystemLogRegistration.log(SystemLogAction.LOGIN_SUCESSFULLY);
	}
	
	private void updateUserAfterLogin(User user) {
		
	}
}
