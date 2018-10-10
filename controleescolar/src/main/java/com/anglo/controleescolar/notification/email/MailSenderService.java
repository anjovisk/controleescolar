package com.anglo.controleescolar.notification.email;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MailSenderService {
	void sendEmail(String from, String to, String subject, String msg);
	void sendEmail(String from, String[] to, String subject, String msg);
	void sendEmail(final Map<String, Object> model, final String templateName, final String from, final String subject);
	void sendEmail(final List<Map<String, Object>> models, final String templateName, final String from);
	void sendEmail(final Map<String, Object> model, final String templateName, final String from, String[] to,
			final String subject, File file);
	void sendEmail(final Map<String, Object> model, final String templateName, final String from, String[] to, 
			final String subject);
	void saveEmailParameters(EmailParameters emailParameters);
	EmailParameters getEmailParameters();
	boolean isConfigured();
}
