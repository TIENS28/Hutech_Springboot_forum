package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.User;

public interface iUserService {
	UserDTO save(UserDTO userDTO, Long id);
	
	void delete(Long id);
	
	List<UserDTO> findAll();
	
	UserDTO findByEmail(String email);
		
	public List<UserDTO> searchUser(String query);
	
	UserDTO getUserProfile(Long userId);

    List<UserDTO> getFollowingUsers();

    List<UserDTO> getFollowers();

    boolean isFollowingUser(Long followerId, Long followingUserId);

//	UserDTO followUser(Long followingId);
    void followUser(Long followingUserId);
	void unfollowUser(Long followingUserId);

}
