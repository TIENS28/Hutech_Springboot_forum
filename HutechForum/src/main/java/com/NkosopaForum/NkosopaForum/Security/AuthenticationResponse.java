package com.NkosopaForum.NkosopaForum.Security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
	private String token;
	private String firstName;
	private String lastName;
	private String fullName;
	private String DOB;
	private Role role;
	private String studentID;
	private String department;
	private LocalDateTime createdDate;
	private Long avatarFileSize;
	private String avatarUrl;
	private String message;
	
	@Builder.Default
	private List<Post> post = new ArrayList<>();
	
}
