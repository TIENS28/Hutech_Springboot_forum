package com.NkosopaForum.NkosopaForum.Services;

import org.springframework.web.multipart.MultipartFile;

import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationRequest;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationResponse;
import com.NkosopaForum.NkosopaForum.Security.RegisterRequest;

public interface iAuthenticationService {

	AuthenticationResponse registerWithVerification(RegisterRequest request, MultipartFile avatar);

	String uploadImageToCloudinary(MultipartFile image);

	AuthenticationResponse authenticate(AuthenticationRequest request);

	User getCurrentUser();

	void deleteFromCloudinary(String imageUrl);

}
