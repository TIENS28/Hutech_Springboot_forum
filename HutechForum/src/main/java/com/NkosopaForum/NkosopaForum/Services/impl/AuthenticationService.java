package com.NkosopaForum.NkosopaForum.Services.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.NkosopaForum.NkosopaForum.Entity.Role;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.UserRepository;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationRequest;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationResponse;
import com.NkosopaForum.NkosopaForum.Security.RegisterRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private Cloudinary cloudinary;

	public AuthenticationResponse register(RegisterRequest request, MultipartFile avatar) {
		// TODO Auto-generated method stub
		if (userRepo.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email is already bind with other account!");
		}
		
		String avatarUrl = uploadAvatarToCloudinary(avatar);

		var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .DOB(request.getDOB())
                .department(request.getDepartment())
                .studentID(request.getStudentID())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .avatarUrl(avatarUrl) // Set the avatar URL in the User(Entity)
                .build();

		userRepo.save(user);

		User savedUser = userRepo.findById(user.getId()).orElse(null);

		if (savedUser != null) {
			LocalDateTime createdDate = savedUser.getCreatedDate();

			String jwtToken = jwtService.generateToken(savedUser);
			return AuthenticationResponse.builder().token(jwtToken).createdDate(LocalDateTime.now()).build();
		} else {
			return null;
		}
	}
	
	//upload avatar to cloudinary
	private String uploadAvatarToCloudinary(MultipartFile avatar) {
	    try {
	        Map uploadResult = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
	        return (String) uploadResult.get("url");
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to upload avatar to Cloudinary", e);
	    }
	}

	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		// TODO Auto-generated method stub
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			var user = userRepo.findByEmail(request.getEmail()).orElseThrow();

			var jwtToken = jwtService.generateToken(user);
			return AuthenticationResponse.builder()
	                .token(jwtToken)
	                .firstName(user.getFirstName())
	                .lastName(user.getLastName())
	                .fullName(user.getFullName())
	                .DOB(user.getDOB())
	                .studentID(user.getStudentID())
	                .department(user.getDepartment())
	                .avatarUrl(user.getAvatarUrl()) // Include the avatar URL
	                .build();
		} catch (Exception e) {
			throw new RuntimeException("Authentication failed", e);
		}
	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof User) {
			return (User) authentication.getPrincipal();
		}
		return null;
	}

}
