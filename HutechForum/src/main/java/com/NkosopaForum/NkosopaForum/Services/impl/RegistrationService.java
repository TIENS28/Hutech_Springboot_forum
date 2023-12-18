package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.UserRepository;
import com.NkosopaForum.NkosopaForum.Services.iRegistrationService;

@Service
public class RegistrationService implements iRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void register(User user) {
        user.setStatus(false); // You can use the status field for verification status
        user.setVerificationToken(UUID.randomUUID().toString());
        userRepository.save(user);

        // Send the verification email
        sendVerificationEmail(user.getEmail(), user.getVerificationToken());
    }

    private void sendVerificationEmail(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("HUTECH Forum - Email Verification");
        mailMessage.setFrom("hutechforum@gmail.com");

        String verificationLink = "http://localhost:5001/api/auth/confirm?token=" + token;
        String emailBody = "Please click the following link to verify your email:\n" + verificationLink;

        mailMessage.setText(emailBody);

        javaMailSender.send(mailMessage);
    }

    @Override
    public boolean confirmRegistration(String token) {
        // Find the user by the verification token
        User user = userRepository.findByVerificationToken(token);

        if (user != null) {
            // Set the user's status to true and remove the verification token
            user.setStatus(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}

