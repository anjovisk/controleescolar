package com.anglo.controleescolar.notification.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.DefaultPropertiesPersister;

import com.anglo.controleescolar.util.Base64Utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Component
public class MailSenderServiceImpl implements MailSenderService {
	private Configuration templateConfiguration;
	@Resource(name = "javaMailSenderAsync")
	private MailSenderAsync mailSender;
	private final String applicationPath;
	@Autowired
	public MailSenderServiceImpl() {
		try {
			applicationPath = URLDecoder.decode(MailSenderService.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
			templateConfiguration = new Configuration(new Version(2, 3, 28));
			String pathName = applicationPath + File.separator + "email-templates";
			templateConfiguration.setDirectoryForTemplateLoading(new File(pathName));
			templateConfiguration.setDefaultEncoding("UTF-8");
			templateConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void sendEmail(String from, String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}
	
	public void sendEmail(String from, String[] to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}
	
	public void sendEmail(final Map<String, Object> model, final String templateName, 
			final String from, final String subject) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setTo(model.get("email").toString());
                message.setFrom(from);
                message.setSubject(subject);
                Template template = templateConfiguration.getTemplate(templateName);
                String text  = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                message.setText(text, true);
            }
		};
        mailSender.send(preparator);
    }
	
	public void sendEmail(final Map<String, Object> model, final String templateName, 
			final String from, String[] to, final String subject, File file) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setTo(to);
                message.setFrom(from);
                message.setSubject(subject);
                message.addAttachment(file.getName(), file);
                Template template = templateConfiguration.getTemplate(templateName);
                String text  = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                message.setText(text, true);
            }
		};
        mailSender.send(preparator);
    }
	
	public void sendEmail(final List<Map<String, Object>> models, final String templateName, 
			final String from) {
		MimeMessagePreparator[] messages = new MimeMessagePreparator[models.size()];
		for (int i = 0; i < models.size(); i++) {
			final Map<String, Object> model = models.get(i);
	        messages[i] = new MimeMessagePreparator() {
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
	                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	                message.setTo(model.get("email").toString());
	                message.setFrom(from);
	                message.setSubject(model.get("subject").toString());
	                Template template = templateConfiguration.getTemplate(templateName);
	                String text  = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	                message.setText(text, true);
	            }
			};
		}
        mailSender.send(messages);
    }
	
	public void sendEmail(final Map<String, Object> model, final String templateName, final String from, String[] to, final String subject) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setTo(to);
                message.setFrom(from);
                message.setSubject(subject);
                Template template = templateConfiguration.getTemplate(templateName);
                String text  = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                message.setText(text, true);
            }
		};
        mailSender.send(preparator);
    }
	
	public void saveEmailParameters(EmailParameters emailParameters) {
		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.quitwait", String.valueOf(emailParameters.getSmtpQuitwait()));
			props.setProperty("mail.smtp.auth", String.valueOf(emailParameters.getSmtpAuth()));
			props.setProperty("mail.smtp.starttls.enable", String.valueOf(emailParameters.getSmtpStarttls()));
			props.setProperty("mail.protocol", emailParameters.getProtocol());
			props.setProperty("mail.host", emailParameters.getHost());
			props.setProperty("mail.port", String.valueOf(emailParameters.getPort()));
			props.setProperty("mail.username", emailParameters.getUsername());
			props.setProperty("mail.password", Base64Utils.getBase64EncodedString(emailParameters.getPassword()));
			String pathName = applicationPath + File.separator + "mail.properties";
			File file = new File(pathName);
			OutputStream out = new FileOutputStream(file);
			DefaultPropertiesPersister p = new DefaultPropertiesPersister();
			p.store(props, out, "Header Comment");
			Properties mailProperties = new Properties();
			mailProperties.put("mail.smtp.auth", emailParameters.getSmtpAuth());
	        mailProperties.put("mail.smtp.starttls.enable", emailParameters.getSmtpStarttls());
	        mailProperties.put("mail.smtp.quitwait", emailParameters.getSmtpQuitwait());
	        mailSender.setJavaMailProperties(mailProperties);
	        mailSender.setHost(emailParameters.getHost());
	        mailSender.setPort(emailParameters.getPort());
	        mailSender.setProtocol(emailParameters.getProtocol());
	        mailSender.setUsername(emailParameters.getUsername());
	        mailSender.setPassword(emailParameters.getPassword());
	   } catch (Exception e ) {
		   e.printStackTrace();
		   throw new RuntimeException(e);
	   }
	}
	
	public EmailParameters getEmailParameters() {
		EmailParameters result = new EmailParameters();
		String pathName = applicationPath + File.separator + "mail.properties";
		File file = new File(pathName);
		Properties mailProperties = new Properties();
		if (file.exists()) {			
			try {
				InputStream inputStream = new FileInputStream(file);
				mailProperties.load(inputStream);
				result.setSmtpAuth(Boolean.parseBoolean(mailProperties.getProperty("mail.smtp.auth")));
				result.setSmtpStarttls(Boolean.parseBoolean(mailProperties.getProperty("mail.smtp.starttls.enable")));
				result.setSmtpQuitwait(Boolean.parseBoolean(mailProperties.getProperty("mail.smtp.quitwait")));
		        result.setHost(mailProperties.getProperty("mail.host"));
		        result.setPort(Integer.parseInt(mailProperties.getProperty("mail.port")));
		        result.setProtocol(mailProperties.getProperty("mail.protocol"));
		        result.setUsername(mailProperties.getProperty("mail.username"));
		        result.setPassword(Base64Utils.getBase64DecodedString(mailProperties.getProperty("mail.password")));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}  else {
			mailProperties.setProperty("mail.protocol", "smtp");
			mailProperties.setProperty("mail.host", "smtp.gmail.com");
			mailProperties.setProperty("mail.port", "587");
			mailProperties.setProperty("mail.username", "teste@tecnoinf.com");
			mailProperties.setProperty("mail.password", Base64Utils.getBase64EncodedString(""));
			mailProperties.setProperty("mail.smtp.quitwait", "false");
			mailProperties.setProperty("mail.smtp.auth", "true");
			mailProperties.setProperty("mail.smtp.starttls.enable", "true");	        
			try {
				OutputStream outputStream = new FileOutputStream(file);
				mailProperties.store(outputStream, "E-mail configuration");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}			
		}
		return result;
	}
	
	public boolean isConfigured() {
		EmailParameters emailParameters = getEmailParameters();
		boolean result = ((emailParameters.getPort() > 0)
				&& (emailParameters.getHost() != null)
				&& (!emailParameters.getHost().equals(""))
				&& (emailParameters.getPassword() != null)
				&& (!emailParameters.getPassword().equals(""))
				&& (emailParameters.getProtocol() != null)
				&& (!emailParameters.getProtocol().equals(""))
				&& (emailParameters.getUsername() != null)
				&& (!emailParameters.getUsername().equals("")));
		return result;
	}
}
