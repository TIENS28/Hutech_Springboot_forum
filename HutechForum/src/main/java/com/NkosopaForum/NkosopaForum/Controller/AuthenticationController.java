package com.NkosopaForum.NkosopaForum.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NkosopaForum.NkosopaForum.Converter.UserConverter;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationRequest;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationResponse;
import com.NkosopaForum.NkosopaForum.Security.RegisterRequest;
import com.NkosopaForum.NkosopaForum.Services.impl.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
		
	@Autowired
	private AuthenticationService authServicer;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		
		return ResponseEntity.ok(authServicer.register(request));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(authServicer.authenticate(request));
	}
	
	
}
