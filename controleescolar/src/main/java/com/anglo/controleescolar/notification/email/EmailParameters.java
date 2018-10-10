package com.anglo.controleescolar.notification.email;

public class EmailParameters {
	private boolean smtpQuitwait;
	private boolean smtpAuth;
	private boolean smtpStarttls;
	private String protocol;
	private String host;
	private int port;
	private String username;
	private String password;
	
	public boolean getSmtpQuitwait() {
		return smtpQuitwait;
	}
	public void setSmtpQuitwait(boolean smtpQuitwait) {
		this.smtpQuitwait = smtpQuitwait;
	}
	public boolean getSmtpAuth() {
		return smtpAuth;
	}
	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}
	public boolean getSmtpStarttls() {
		return smtpStarttls;
	}
	public void setSmtpStarttls(boolean smtpStarttls) {
		this.smtpStarttls = smtpStarttls;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
