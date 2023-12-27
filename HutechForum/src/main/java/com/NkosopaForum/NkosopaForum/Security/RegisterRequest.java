package com.NkosopaForum.NkosopaForum.Security;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
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
    
    private String email;
    
    private String password;
    
    private String studentID;
    
    private String DOB;
    private String department;
    
    @Nullable
    private MultipartFile avatar;
}

