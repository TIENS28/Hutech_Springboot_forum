package com.NkosopaForum.NkosopaForum.Services.impl;

import java.io.IOException;
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

	@Autowired
    private RegistrationService registrationService;
	
	// REGISTER AND AUTHENTICATE METHOD
	public AuthenticationResponse registerWithVerification(RegisterRequest request, MultipartFile avatar) {
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
                .avatarUrl(avatarUrl)
                .build();

        userRepo.save(user);

        // Call the registration service to initiate email verification
        registrationService.register(user);

        // Generate and return the authentication response with a message
        return AuthenticationResponse.builder()
                .message("Registration successful. Check your email for verification.")
                .build();
    }
	
	public String uploadAvatarToCloudinary(MultipartFile avatar) {
	    try {
	        Map uploadResult = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
	        return (String) uploadResult.get("url");
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to upload avatar to Cloudinary", e);
	    }
	}

	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            // Check if the user is active before generating the token
            User user = (User) authentication.getPrincipal();
            if (!user.isStatus()) {
                throw new RuntimeException("User account is not active. Please verify your email.");
            }

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .fullName(user.getFullName())
                    .DOB(user.getDOB())
                    .studentID(user.getStudentID())
                    .department(user.getDepartment())
                    .avatarUrl(user.getAvatarUrl())
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
