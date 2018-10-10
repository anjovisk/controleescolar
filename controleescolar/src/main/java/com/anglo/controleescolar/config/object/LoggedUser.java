package com.anglo.controleescolar.config.object;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoggedUser extends org.springframework.security.core.userdetails.User {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5857444067950734444L;
	private User user;
	
	public LoggedUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getLogin(), user.getPassword() == null ? user.getLogin() : user.getPassword(), authorities);
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
}
