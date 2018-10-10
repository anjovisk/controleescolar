package com.anglo.controleescolar.notification;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anglo.controleescolar.config.properties.ApplicationProperties;
import com.anglo.controleescolar.notification.email.MailSenderService;

@Component
class NotificationService {
	@Autowired
	private MailSenderService mailSenderService;
	@Resource
	private ApplicationProperties applicationProperties;
	String getAdminEmail() {
		return mailSenderService.getEmailParameters().getUsername();
	}
	
	void sendCustomizedEmail(String templateName, String subjectMessage, Map<String, Object> mailModel) {
		fillModel(mailModel, subjectMessage);
		mailSenderService.sendEmail(mailModel, templateName, mailSenderService.getEmailParameters().getUsername(), subjectMessage);
	}
	
	void sendCustomizedEmail(String templateName, String subjectMessage, List<Map<String, Object>> mailModels) {
		fillModel(mailModels, subjectMessage);
		mailSenderService.sendEmail(mailModels, templateName, mailSenderService.getEmailParameters().getUsername());
	}
	
	void sendCustomizedEmail(String templateName, String subjectMessage, List<String> to, Map<String, Object> mailModel) {
		fillModel(mailModel, subjectMessage);
		mailSenderService.sendEmail(mailModel, templateName, mailSenderService.getEmailParameters().getUsername(), to.toArray(new String[to.size()]), subjectMessage);
	}
	
	void sendEmail(String templateName, String subjectMessage, List<Map<String, Object>> mailModels) {
		sendCustomizedEmail(templateName, subjectMessage, mailModels);
	}
	
	void sendEmail(String templateName, String subjectMessage, List<String> to, Map<String, Object> mailModel) {
		sendCustomizedEmail(templateName, subjectMessage, to, mailModel);
	}
	
	void sendEmail(String templateName, String subjectMessage, Map<String, Object> mailModel) {
		sendCustomizedEmail(templateName, subjectMessage, mailModel);
	}
	
	private void fillModel(List<Map<String, Object>> mailModels, String subject) {
		for (Map<String, Object> mailModel : mailModels) {
			fillModel(mailModel, subject);
		}
	}
	
	private void fillModel(Map<String, Object> mailModel, String subject) {
		String baseUrl = applicationProperties.getDefaultUrl();
		mailModel.put("baseUrl", baseUrl);	
		mailModel.put("enterpriseName", applicationProperties.getEnterpriseName());
		mailModel.put("subject", subject);
	}
}
