package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import com.NkosopaForum.NkosopaForum.DTO.UserDTO;

public interface iUserService {
	UserDTO save(UserDTO userDTO, Long id);
	void delete(Long id);
	List<UserDTO> findAll();
}
