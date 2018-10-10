package com.anglo.controleescolar.notification;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("NotificationServiceImpl")
public class NotificationServiceImpl {
	@Resource
	private MessageSource messageSource;
	@Autowired
	private NotificationService notificationService;
	
	public void testEmailConfiguration(String to) {
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.clear();
		mailModel.put("email", (to == null || to.isEmpty()) ? notificationService.getAdminEmail() : to);
		String subject = messageSource.getMessage("mail.subject.email.test", null, null);
		notificationService.sendCustomizedEmail("test-email.template", subject, mailModel);
	}
}
