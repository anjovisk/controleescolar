package com.anglo.controleescolar.config.object;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
	User getEnabledUser(String userName, boolean loadAuthorities);
}
