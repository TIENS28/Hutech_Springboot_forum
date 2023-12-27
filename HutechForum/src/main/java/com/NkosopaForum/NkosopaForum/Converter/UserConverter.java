package com.NkosopaForum.NkosopaForum.Converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.User;

@Component
public class UserConverter {
	@Autowired
	@Lazy
	private PostConverter postConverter;
	
	@Autowired
	@Lazy
	private RoleConverter roleConverter;
	
	
	public User UserToEntity(UserDTO dto) {
		User entity = new User();
		updateEntityFromDTO(entity, dto);
		entity.setCreatedDate(LocalDateTime.now());
		
		return entity;
	}

	public UserDTO EnitytoDTO(User user) {
	    UserDTO dto = new UserDTO();
	    if (user != null && user.getId() != null) {
	        dto.setId(user.getId());
	    }
	    if (user != null) {
	    	dto.setFirstName(user.getFirstName());
	    	dto.setLastName(user.getLastName());
	        dto.setFullName(user.getFullName());
	        dto.setPassword(user.getPassword());
	        dto.setCreatedDate(user.getCreatedDate());
	        dto.setEmail(user.getEmail());
	        dto.setRole(roleConverter.toDTO(user.getRole()));
	        dto.setDOB(user.getDOB());
	        dto.setDepartment(user.getDepartment());
	        dto.setStudentID(user.getStudentID());
	        dto.setAvatarUrl(user.getAvatarUrl());

	    }
	    return dto;
	}

//	Non-used method, extends in future 
//	public List<UserDTO> EnitytoDTO(List<User> followers) {
//	    List<UserDTO> userDTOs = new ArrayList<>();
//
//	    for (User user : followers) {
//	        // Initialize the 'follower' and 'following' collections within an active Hibernate session
//	        Hibernate.initialize(user.getFollower());
//	        Hibernate.initialize(user.getFollowing());
//
//	        UserDTO dto = new UserDTO();
//	        dto.setId(user.getId());
//	        dto.setFullName(user.getFullName());
//	        dto.setCreatedDate(user.getCreatedDate());
//	        dto.setRole(roleConverter.toDTO(user.getRole()));
//	        dto.setEmail(user.getEmail());
//	        dto.setDOB(user.getDOB());
//	        dto.setDepartment(user.getDepartment());
//	        dto.setStudentID(user.getStudentID());
//	        userDTOs.add(dto);
//	    }
//
//	    return userDTOs;
//	}

// for update user information
	
	public User DtoToEnity(UserDTO dto, User entity) {
		if (dto.getId() == null) {
			return null;
		} else {
			updateEntityFromDTO(entity, dto);
			entity.setModifiedDate(LocalDateTime.now());
			entity.setModifiedBy(dto.getFirstName() + " " + dto.getLastName());
			return entity;
		}
	}
	
	//avoid duplicate
	private void updateEntityFromDTO(User entity, UserDTO dto) {
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setFullName(dto.getFirstName() + " " + dto.getLastName());
		entity.setDOB(dto.getDOB());
		entity.setDepartment(dto.getDepartment());
		entity.setStudentID(dto.getStudentID());
	}
	
	 public List<UserDTO> toDTOList(List<User> users) {
	        return users.stream()
	                .map(this::EnitytoDTO)
	                .collect(Collectors.toList());
	 }
}
