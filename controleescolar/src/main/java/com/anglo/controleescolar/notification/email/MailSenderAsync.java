package com.anglo.controleescolar.notification.email;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MailSenderAsync extends JavaMailSenderImpl implements JavaMailSender {
	@Resource(name = "taskExecutor") 
	private TaskExecutor taskExecutor;
	 
	@Autowired
	public MailSenderAsync(TaskExecutor taskExecutor){
		this.taskExecutor = taskExecutor;
	}
	
	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		taskExecutor.execute(new SimpleMailMessageRunnable(simpleMessage));
	}
	
	@Override
	public void send(MimeMessagePreparator mimeMessagePreparator) {
		taskExecutor.execute(new MimeMessagePreparatorRunnable(mimeMessagePreparator));
	}
	
	@Override
	public void send(MimeMessage mimeMessage) {
		taskExecutor.execute(new MimeMessageRunnable(mimeMessage));
	}
	
	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		taskExecutor.execute(new SimpleMailMessagesRunnable(simpleMessages));
	}
	
	@Override
	public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
		taskExecutor.execute(new MimeMessagePreparatorsRunnable(mimeMessagePreparators));
	}
	
	@Override
	public void send(MimeMessage... mimeMessages) throws MailException {
		taskExecutor.execute(new MimeMessagesRunnable(mimeMessages));
	}
	
	private void sendAsync(SimpleMailMessage simpleMailMessage) {
		super.send(new SimpleMailMessage[] {simpleMailMessage});
	}
	
	private void sendAsync(MimeMessagePreparator mimeMessagePreparator) {
		super.send(new MimeMessagePreparator[] {mimeMessagePreparator});
	}
	
	private void sendAsync(MimeMessage mimeMessage) {
		super.send(new MimeMessage[] {mimeMessage});
	}
	 
	private class SimpleMailMessageRunnable implements Runnable {
	   private SimpleMailMessage simpleMailMessage;
	   private SimpleMailMessageRunnable(SimpleMailMessage simpleMailMessage) {
	     this.simpleMailMessage = simpleMailMessage;
	   }
	 
	   public void run() {
		   sendAsync(simpleMailMessage);
	   }
	}
	
	private class MimeMessageRunnable implements Runnable {
	   private MimeMessage mimeMessage;
	   private MimeMessageRunnable(MimeMessage mimeMessage) {
	     this.mimeMessage = mimeMessage;
	   }
	 
	   public void run() {
		   sendAsync(mimeMessage);
	   }
	}
	
	private class MimeMessagePreparatorRunnable implements Runnable {
		   private MimeMessagePreparator mimeMessage;
		   private MimeMessagePreparatorRunnable(MimeMessagePreparator mimeMessagePreparator) {
		     this.mimeMessage = mimeMessagePreparator;
		   }
		 
		   public void run() {
			   sendAsync(mimeMessage);
		   }
		}
	
	private class SimpleMailMessagesRunnable implements Runnable {
	   private SimpleMailMessage[] simpleMessages;
	   private SimpleMailMessagesRunnable(SimpleMailMessage[] 
	      simpleMessages) {
	     this.simpleMessages = simpleMessages;
	   }
	 
	   public void run() {
		   for (SimpleMailMessage message : simpleMessages) {
			   try {
					sendAsync(message);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
	   }
	}
	
	private class MimeMessagesRunnable implements Runnable {
	   private MimeMessage[] mimeMessages;
	   private MimeMessagesRunnable(MimeMessage[] 
	      simpleMessages) {
	     this.mimeMessages = simpleMessages;
	   }
	 
	   public void run() {
		   for (MimeMessage message : mimeMessages) {
				try {
					sendAsync(message);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
	   }
	}
	
	private class MimeMessagePreparatorsRunnable implements Runnable {
	   private MimeMessagePreparator[] mimeMessages;
	   private MimeMessagePreparatorsRunnable(MimeMessagePreparator[] 
	      simpleMessages) {
	     this.mimeMessages = simpleMessages;
	   }
	 
	   public void run() {
		   for (MimeMessagePreparator message : mimeMessages) {
				try {
					sendAsync(message);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
	   }
	}
}