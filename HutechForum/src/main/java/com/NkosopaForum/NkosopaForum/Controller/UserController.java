package com.NkosopaForum.NkosopaForum.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Services.impl.UserService;

@RestController
@RequestMapping("/api/auth/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PutMapping("/updateProfile")
    public UserDTO updateProfile(@ModelAttribute UserDTO userDTO,
                                 @RequestParam(name = "avatar", required = false) MultipartFile avatar) {
        userDTO.setAvatar(avatar);
        return userService.updateProfile(userDTO);
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
    @GetMapping("/followers")
    public ResponseEntity<List<UserDTO>> getCurrentUserFollowers() {
        List<UserDTO> followers = userService.getFollowers();
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following")
    public ResponseEntity<List<UserDTO>> getCurrentUserFollowing() {
        List<UserDTO> following = userService.getFollowingUsers();
        return ResponseEntity.ok(following);
    }


    // Check if a user is following another
    @GetMapping("/isFollowing/{followerId}/{followingUserId}")
    public ResponseEntity<Boolean> isFollowingUser(
            @PathVariable Long followerId, @PathVariable Long followingUserId) {
        boolean isFollowing = userService.isFollowingUser(followerId, followingUserId);
        return ResponseEntity.ok(isFollowing);
    }

    @PostMapping("/follow/{followingId}")
    public ResponseEntity<String> followUser(@PathVariable Long followingId) {
        userService.followUser(followingId);
        return ResponseEntity.ok("User followed successfully.");
    }

    @PostMapping("/unfollow/{followingId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followingId) {
        userService.unfollowUser(followingId);
        return ResponseEntity.ok("User unfollowed successfully.");
    }
}