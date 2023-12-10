package com.NkosopaForum.NkosopaForum.Security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	    private String firstName;
	    private String lastName;
	    private String mail;
	    private String password;
		
	    

}
