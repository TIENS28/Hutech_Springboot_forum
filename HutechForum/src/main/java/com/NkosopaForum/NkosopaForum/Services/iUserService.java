package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.User;

public interface iUserService {
	
	void delete(Long id);
	
	List<UserDTO> findAll();
	UserDTO updateProfile(UserDTO userDTO);
	UserDTO findByEmail(String email);
		
	public List<UserDTO> searchUser(String query);
	
	UserDTO getUserProfile(Long userId);

    List<UserDTO> getFollowingUsers();

    List<UserDTO> getFollowers();

    boolean isFollowingUser(Long followerId, Long followingUserId);

//	UserDTO followUser(Long followingId);
    void followUser(Long followingUserId);
	void unfollowUser(Long followingUserId);

	List<PostDTO> getPostsForCurrentUser();

//	UserDTO save(UserDTO userDTO);
}
