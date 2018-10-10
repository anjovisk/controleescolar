package com.anglo.controleescolar.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.anglo.controleescolar.notification.email.MailSenderAsync;
import com.anglo.controleescolar.util.Base64Utils;

@Configuration
@ComponentScan(basePackages = { "com.anglo.controleescolar.service", "com.anglo.controleescolar.util", 
		"com.anglo.controleescolar.repository", "com.anglo.controleescolar.controller.validator", 
		"com.anglo.controleescolar.report", "com.anglo.controleescolar.notification", "com.anglo.controleescolar.logging",
		"com.anglo.controleescolar.config.properties", "com.anglo.controleescolar.exception" })
public class BasicConfig {
	
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(5);
		pool.setMaxPoolSize(10);
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}
	
	@Bean
    public JavaMailSender javaMailSenderAsync(ThreadPoolTaskExecutor taskExecutor, ServletContext servletContext) {
		MailSenderAsync mailSender = new MailSenderAsync(taskExecutor);
		String applicationPath = servletContext.getRealPath("");
		File file = new File(applicationPath + "/WEB-INF/classes/mail.properties");
		if (file.exists()) {
			try {
				Properties mailProperties = new Properties();
				InputStream inputStream = new FileInputStream(file);
				mailProperties.load(inputStream);
				Properties smtpProperties = new Properties();
				smtpProperties.put("mail.smtp.auth", mailProperties.getProperty("mail.smtp.auth"));
				smtpProperties.put("mail.smtp.starttls.enable", mailProperties.getProperty("mail.smtp.starttls.enable"));
				smtpProperties.put("mail.smtp.quitwait", mailProperties.getProperty("mail.smtp.quitwait"));
		        mailSender.setJavaMailProperties(smtpProperties);
		        mailSender.setHost(mailProperties.getProperty("mail.host"));
		        mailSender.setPort(Integer.parseInt(mailProperties.getProperty("mail.port")));
		        mailSender.setProtocol(mailProperties.getProperty("mail.protocol"));
		        mailSender.setUsername(mailProperties.getProperty("mail.username"));
		        mailSender.setPassword(Base64Utils.getBase64DecodedString(mailProperties.getProperty("mail.password")));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
        return mailSender;
    }
	
	@Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:message");
        return resource;
    }
}
