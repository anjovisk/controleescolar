package com.anglo.controleescolar.config.object;

import java.util.List;

public interface User {
	String getLogin();
	String getPassword();
	List<UserGroup> getUserGroups();
}
