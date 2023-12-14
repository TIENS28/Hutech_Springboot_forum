package com.NkosopaForum.NkosopaForum.Services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.NkosopaForum.NkosopaForum.Entity.Role;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.UserRepository;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationRequest;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationResponse;
import com.NkosopaForum.NkosopaForum.Security.RegisterRequest;

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
	
	public AuthenticationResponse register(RegisterRequest request) {
		// TODO Auto-generated method stub
		if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already bind with other account!");
        }
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
				.build();
	
		userRepo.save(user);
		
		User savedUser = userRepo.findById(user.getId()).orElse(null);

        if (savedUser != null) {
            LocalDateTime createdDate = savedUser.getCreatedDate();

            String jwtToken = jwtService.generateToken(savedUser);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .createdDate(LocalDateTime.now())  
                    .build();
        } else {
            return null;
        }
    }

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		// TODO Auto-generated method stub
		try {
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getEmail(),
	                        request.getPassword())
	        );

	        var user = userRepo.findByEmail(request.getEmail())
	                .orElseThrow();

	        var jwtToken = jwtService.generateToken(user);
	        return AuthenticationResponse.builder()
	                .token(jwtToken)
	                .firstName(user.getFirstName())
	                .lastName(user.getLastName())
	                .fullName(user.getFullName())
	                .DOB(user.getDOB())
	                .studentID(user.getStudentID())
	                .department(user.getDepartment())
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
