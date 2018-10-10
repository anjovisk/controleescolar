package com.anglo.controleescolar.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anglo.controleescolar.config.object.AuthenticationService;
import com.anglo.controleescolar.config.object.Authority;
import com.anglo.controleescolar.config.object.LoggedUser;
import com.anglo.controleescolar.config.object.User;
import com.anglo.controleescolar.config.object.UserGroup;

@Service("AuthenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
	@Autowired
	protected MessageSource messageSource;
	private final String administratorUserName = "administrador";
	
	@Override
	public UserDetails loadUserByUsername(String userName) {
		User user = getEnabledUser(userName, true);
		if (user == null) {
			throw new UsernameNotFoundException(messageSource.getMessage("message.user.not.found", null, null));
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (UserGroup userGroup : user.getUserGroups()) {
			for (Authority authority : userGroup.getAuthorities()) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
				if (!grantedAuthorities.contains(grantedAuthority)) {
					grantedAuthorities.add(grantedAuthority);
				}
			}
		}
		if (grantedAuthorities.isEmpty()) {
			throw new UsernameNotFoundException(messageSource.getMessage("message.the.user.has.no.authorities", null, null));
		}
		UserDetails userDetails = new LoggedUser(user, grantedAuthorities);
		return userDetails;
	}
	
	@Override
	public User getEnabledUser(String userName, boolean loadAuthorities) {
		if (!administratorUserName.equals(userName)) {
			return null;
		}
		return new User() {
			@Override
			public List<UserGroup> getUserGroups() {
				if (!loadAuthorities) {
					return null;
				}
				List<UserGroup> result = new ArrayList<>();
				result.add(new UserGroup() {
					
					@Override
					public List<Authority> getAuthorities() {
						List<Authority> result = new ArrayList<>();
						result.add(new Authority() {
							@Override
							public String getName() {
								return "ROLE_USER";
							}
						});
						return result;
					}
				});
				return result;
			}
			
			@Override
			public String getPassword() {
				return "$2a$10$rBx3/uVvTLbpe0.YD4poA.jC6YziXM0KLJ705D.pf9d/LpKmjQLMC";
			}
			
			@Override
			public String getLogin() {
				return administratorUserName;
			}
		};
	}
}
