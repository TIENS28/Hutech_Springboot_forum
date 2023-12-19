package com.NkosopaForum.NkosopaForum.Security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.NkosopaForum.NkosopaForum.Entity.Post;

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
	private String studentID;
	private String department;
	private LocalDateTime createdDate;
	private Long avatarFileSize;
	private String avatarUrl;
	private String message; // Add a message field
	private List<Post> post = new ArrayList<>();
	
}
