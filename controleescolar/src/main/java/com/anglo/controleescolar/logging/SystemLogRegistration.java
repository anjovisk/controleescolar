package com.anglo.controleescolar.logging;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.anglo.controleescolar.config.object.LoggedUser;
import com.anglo.controleescolar.repository.SystemLogRepository;
import com.anglo.controleescolar.repository.entity.SystemLog;

@Component
public class SystemLogRegistration {
	private static SystemLogRepository systemLogRepository;
	private static MessageSource messageSource;
	@Autowired
	private MessageSource messageSourceInstance;
	@Autowired
	private SystemLogRepository systemLogRepositoryInstance;
	
	@PostConstruct
	private void init() {
		systemLogRepository = this.systemLogRepositoryInstance;
		messageSource = this.messageSourceInstance;
	}
	
	@Transactional
	public static void log(SystemLogAction action) {
		SystemLog systemLog = new SystemLog();
		systemLog.setAction(action);
		systemLog.setActionDate(LocalDateTime.now());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = messageSource.getMessage("caption.anonymous", null, null);
		if (authentication.isAuthenticated() && authentication.getPrincipal().getClass().equals(LoggedUser.class)) {
			LoggedUser loggedUser = ((LoggedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			userName = loggedUser.getUsername();
		}
		systemLog.setUserLogin(userName);
		systemLogRepository.save(systemLog);
	}
}
