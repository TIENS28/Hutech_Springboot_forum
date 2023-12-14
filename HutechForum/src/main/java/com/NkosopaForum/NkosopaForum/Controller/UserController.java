package com.NkosopaForum.NkosopaForum.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Services.impl.UserService;

@RestController
@RequestMapping("/api/auth/users")
@CrossOrigin(origins = "http://localhost:5001")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/updateUser/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        return userService.save(userDTO, id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/user/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/searchUser/{query}")
    public ResponseEntity<List<UserDTO>> searchUser(@PathVariable String query) {
        List<UserDTO> users = userService.searchUser(query);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/userProfile/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long userId) {
        UserDTO userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    // Get followers
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable Long userId) {
        List<UserDTO> followers = userService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    // Get following users
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserDTO>> getFollowing(@PathVariable Long userId) {
        List<UserDTO> following = userService.getFollowingUsers(userId);
        return ResponseEntity.ok(following);
    }

    // Check if a user is following another
    @GetMapping("/isFollowing/{followerId}/{followingUserId}")
    public ResponseEntity<Boolean> isFollowingUser(
            @PathVariable Long followerId, @PathVariable Long followingUserId) {
        boolean isFollowing = userService.isFollowingUser(followerId, followingUserId);
        return ResponseEntity.ok(isFollowing);
    }

    @PostMapping("/followUser/{followerId}/{followingUserId}")
    public ResponseEntity<UserDTO> followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingUserId) {
        
        UserDTO updatedUser = userService.followUser(followerId, followingUserId);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/unfollowUser/{followerId}/{followingUserId}")
    public ResponseEntity<UserDTO> unfollowUser(
            @PathVariable Long followerId, @PathVariable Long followingUserId) {
        UserDTO userDTO = userService.unfollowUser(followerId, followingUserId);
        return userDTO != null ?
                ResponseEntity.ok(userDTO) :
                ResponseEntity.notFound().build();
    }
}