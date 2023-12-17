package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NkosopaForum.NkosopaForum.Converter.UserConverter;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
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
	
	//save or update user service
	@Override
	public UserDTO save(UserDTO userDTO, Long id) {
		User newUser = new User();
		//user not exist yet, create new user
		if(userDTO.getId() == null) {
			newUser = userConverter.UserToEntity(userDTO);
		}else {
			//if user is exist, update user information
			Optional<User> optionalUser = userRepo.findById(userDTO.getId());
			if(optionalUser.isPresent()) {
				User oldUser = optionalUser.get();
				newUser = userConverter.DtoToEnity(userDTO, oldUser);
			}
		}
		
		userRepo.save(newUser);
		return userConverter.EnitytoDTO(newUser);
	}
	
	//delete user by id
	@Override
	public void delete(Long id) {
		 Optional<User> optionalUser = userRepo.findById(id);
		    
		    if (optionalUser.isPresent()) {
		        User user = optionalUser.get();
		        
		        //delete all user's posts
		        user.getPost().clear();
		        
		        //delete user from database
		        userRepo.deleteById(id);
		    } else {
		        throw new EntityNotFoundException("User not found with ID: " + id);
		    }
	}

	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> rs = new ArrayList<>();
		List<User> users = userRepo.findAll();
		for(User item: users) {
			UserDTO userDto = userConverter.EnitytoDTO(item);
			rs.add(userDto);
		}
	    return rs;
	}

	@Override
	public UserDTO findByEmail(String email) {
		// TODO Auto-generated method stub
		Optional<User> userOptional = userRepo.findByEmail(email);
		if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        return userConverter.EnitytoDTO(user);
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
	    public List<UserDTO> getFollowingUsers(Long userId) {
	        Optional<User> userOptional = userRepo.findById(userId);
	        return userOptional.map(user -> userConverter.EnitytoDTO(user.getFollowing())).orElse(Collections.emptyList());
	    }

	 @Override
	    public List<UserDTO> getFollowers(Long userId) {
	        Optional<User> userOptional = userRepo.findById(userId);
	        return userOptional.map(user -> userConverter.EnitytoDTO(user.getFollower())).orElse(Collections.emptyList());
	    }

	 @Override
	 public boolean isFollowingUser(Long followerId, Long followingUserId) {
		 // Retrieve the follower and following users by their ID
	     Optional<User> followerOptional = userRepo.findById(followerId);
	     Optional<User> followingOptional = userRepo.findById(followingUserId);
	     
	     // Check if both users exist and if the followerOptional is following the followingOptional
	     return followerOptional.flatMap(follower ->
	             followingOptional.map(followingUser -> follower.getFollowing().contains(followingUser)))
	             .orElse(false);
	 }
	 
	 //including follow and unfollow action
	 @Override
	 public UserDTO followUser(Long followerId, Long followingId) {
		 Optional<User> followerOptional = userRepo.findById(followerId);
		 Optional<User> followingOptional = userRepo.findById(followingId);

		    if (followerOptional.isPresent() && followingOptional.isPresent()) {
		    	
		        User follower = followerOptional.get();
		        User followingUser = followingOptional.get();

		        if (follower.getFollowing().contains(followingUser) && followingUser.getFollower().contains(follower)) {
		            // If the relationship already exists, remove the follow
		            follower.getFollowing().remove(followingUser);
		            followingUser.getFollower().remove(follower);
		        } else {
		            // If the relationship does not exist, add the follow
		            follower.getFollowing().add(followingUser);
		            followingUser.getFollower().add(follower);
		        }

		        // Save both users to update the relationship
		        userRepo.save(follower);
		        userRepo.save(followingUser);

		        // Convert the updated follower to DTO and return
		        return userConverter.EnitytoDTO(follower);
		    } else {
		        return null;
		    }
		}
	 
	 //just unfollow action
	 @Override
	 public UserDTO unfollowUser(Long followerId, Long followingUserId) {
	     Optional<User> followerOptional = userRepo.findById(followerId);
	     Optional<User> followingOptional = userRepo.findById(followingUserId);
	     
	      // Checking if the followerOptional is following the followingOptional
	     if (followerOptional.isPresent() && followingOptional.isPresent()) {
	    	 
	         User follower = followerOptional.get();
	         User followingUser = followingOptional.get();
	         
	         if (follower.getFollowing().contains(followingUser) && followingUser.getFollower().contains(follower)) {
	             // If the relationship exists, remove the follow
	        	 follower.getFollowing().remove(followingUser);
	             followingUser.getFollower().remove(follower);
	             
	             userRepo.save(follower);
	             userRepo.save(followingUser);

	             // Convert the updated follower to DTO and return
	             return userConverter.EnitytoDTO(follower);
	         }
	     }
	     
	     return null;
	 }



}