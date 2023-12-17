package com.NkosopaForum.NkosopaForum.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.NkosopaForum.NkosopaForum.Security.AuthenticationRequest;
import com.NkosopaForum.NkosopaForum.Security.AuthenticationResponse;
import com.NkosopaForum.NkosopaForum.Security.RegisterRequest;
import com.NkosopaForum.NkosopaForum.Services.impl.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
		
	@Autowired
	private AuthenticationService authService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@ModelAttribute RegisterRequest request, @RequestParam("avatar") MultipartFile avatar) {
	    return ResponseEntity.ok(authService.register(request, avatar));
	}



	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
	
}
