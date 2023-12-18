package com.NkosopaForum.NkosopaForum.Services;

import com.NkosopaForum.NkosopaForum.Entity.User;

public interface iRegistrationService {
	
	void register(User user);
	boolean confirmRegistration(String token);
}
