package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.NkosopaForum.NkosopaForum.Converter.PostConverter;
import com.NkosopaForum.NkosopaForum.Converter.UserConverter;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.CommentEntity;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.UserRepository;
import com.NkosopaForum.NkosopaForum.Services.iUserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements iUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private PostConverter postConverter;

	// update user profile
	@Override
	public UserDTO updateProfile(UserDTO userDTO) {
	    // Extract user ID from the DTO
	    Long userId = userDTO.getId();
	    if (userId != null) {
	        Optional<User> optionalUser = userRepo.findById(userId);

	        if (optionalUser.isPresent()) {
	            // User found, update the profile
	            User user = optionalUser.get();
	            if (userDTO.getAvatar() != null) {
	                String avatarUrl = authenticationService.uploadAvatarToCloudinary(userDTO.getAvatar());
	                user.setAvatarUrl(avatarUrl);
	            }
	            
	            user = userConverter.DtoToEnity(userDTO, user); // Update user fields

	            userRepo.save(user);

	            return userConverter.EnitytoDTO(user);
	        } else {
	            throw new EntityNotFoundException("User not found with ID: " + userId);
	        }
	    } else {
	        throw new IllegalArgumentException("User ID is required for updating the profile");
	    }
	}


	// delete user by id
	@Override
	public void delete(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			// delete all user's posts/comments
			for (CommentEntity comment : user.getComment()) {
	            comment.setUser(null); 
	        }
	        user.getComment().clear();

	        for (Post post : user.getPost()) {
	            post.setUser(null); 
	        }
	        user.getPost().clear();

			userRepo.deleteById(id);
		} else {
			throw new EntityNotFoundException("User not found with ID: " + id);
		}
	}

	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> rs = new ArrayList<>();
		List<User> users = userRepo.findAll();
		for (User item : users) {
			UserDTO userDto = userConverter.EnitytoDTO(item);
			rs.add(userDto);
		}
		return rs;
	}

	@Override
	public UserDTO findByEmail(String email) {
		Optional<User> userOptional = userRepo.findByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			List<PostDTO> userPostDTOs = postConverter.toDTOList(user.getPost());
			UserDTO userDTO = userConverter.EnitytoDTO(user);
			userDTO.setUserPost(userPostDTOs);
			return userDTO;
		} else {
			return null;
		}
	}

	@Override
	public List<UserDTO> searchUser(String query) {
		List<User> users = userRepo.searchUser(query);
		List<UserDTO> userDTOs = users.stream().map(userConverter::EnitytoDTO).collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public UserDTO getUserProfile(Long userId) {
		Optional<User> userOptional = userRepo.findById(userId);
		return userOptional.map(userConverter::EnitytoDTO).orElse(null);
	}

	@Override
	public List<UserDTO> getFollowingUsers() {
		User currentUser = authenticationService.getCurrentUser();
		return userConverter.EnitytoDTO(currentUser.getFollowing());
	}

	@Override
	public List<UserDTO> getFollowers() {
		User currentUser = authenticationService.getCurrentUser();
		return userConverter.EnitytoDTO(currentUser.getFollower());
	}

	@Override
	public boolean isFollowingUser(Long followerId, Long followingUserId) {
		// Retrieve the follower and following users by their ID
		Optional<User> followerOptional = userRepo.findById(followerId);
		Optional<User> followingOptional = userRepo.findById(followingUserId);

		// Check if both users exist and if the followerOptional is following the
		// followingOptional
		return followerOptional.flatMap(
				follower -> followingOptional.map(followingUser -> follower.getFollowing().contains(followingUser)))
				.orElse(false);
	}

	// folow method
	public void followUser(Long followingUserId) {
		// Get the ID of the currently logged-in user
		Long currentUser = authenticationService.getCurrentUser().getId();

		// Retrieve the users
		User loggedInUser = userRepo.findById(currentUser).orElse(null);
		User followingUser = userRepo.findById(followingUserId).orElse(null);

		if (loggedInUser != null && followingUser != null) {
			// Update the followers and following relationships
			loggedInUser.getFollowing().add(followingUser);
			followingUser.getFollower().add(loggedInUser);

			// Save the changes
			userRepo.save(loggedInUser);
			userRepo.save(followingUser);
		}
	}

	// unfollow user
	public void unfollowUser(Long followingUserId) {
		// Get the ID of the currently login user
		Long currentUser = authenticationService.getCurrentUser().getId();

		// Retrieve the users
		User loggedInUser = userRepo.findById(currentUser).orElse(null);
		User followingUser = userRepo.findById(followingUserId).orElse(null);

		if (loggedInUser != null && followingUser != null) {
			// Remove the relationship
			loggedInUser.getFollowing().remove(followingUser);

			userRepo.save(loggedInUser);
		}
	}

	@Override
	public List<PostDTO> getPostsForCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();
		UserDTO currentUser = findByEmail(userEmail);

		List<PostDTO> userPosts = currentUser.getUserPost();
		return userPosts;
	}

}