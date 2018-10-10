package com.anglo.controleescolar.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.anglo.controleescolar.logging.SystemLogAction;


@Table(name="systemLogs", indexes={
		@Index(name="idx_system_log_userLogin", columnList="userLogin", unique=false),
		@Index(name="idx_system_log_actionDate", columnList="actionDate", unique=false),
		@Index(name="idx_system_log_action", columnList="action", unique=false)})
@javax.persistence.Entity
@Component
public class SystemLog implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6910039474465595258L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;	
	@Column(nullable = false, length=100)
	private String userLogin;	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private LocalDateTime actionDate;	
	@Column(nullable = false, columnDefinition = "TINYINT")
	@Enumerated(EnumType.ORDINAL)
	private SystemLogAction action;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public LocalDateTime getActionDate() {
		return actionDate;
	}
	public void setActionDate(LocalDateTime actionDate) {
		this.actionDate = actionDate;
	}
	public SystemLogAction getAction() {
		return action;
	}
	public void setAction(SystemLogAction action) {
		this.action = action;
	}
}
