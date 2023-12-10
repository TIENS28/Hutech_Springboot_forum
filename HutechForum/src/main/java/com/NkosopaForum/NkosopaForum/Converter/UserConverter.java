package com.NkosopaForum.NkosopaForum.Converter;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.User;

@Component
public class UserConverter {
	@Autowired
	@Lazy
	private PostConverter postConverter;

	// for new user
	public User UserToEntity(UserDTO dto) {
		User entity = new User();
		entity.setCreatedDate(LocalDateTime.now());
		return entity;
	}

	public UserDTO EnitytoDTO(User user) {
		UserDTO dto = new UserDTO();
		if (user.getId() != null) {
			dto.setId(user.getId());
		}
		dto.setFullName(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setCreatedDate(user.getCreatedDate());
		dto.setMail(user.getEmail());
		dto.setStatus(user.isStatus());
		
		return dto;
	}

	// for update user information
	public User DtoToEnity(UserDTO dto, User entity) {
		if (dto.getId() == null) {
			return null;
		} else {
			entity.setEmail(dto.getMail());
			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			entity.setFullName(dto.getFirstName() + " " + dto.getLastName());
			entity.setStatus(dto.isStatus());
			entity.setModifiedDate(LocalDateTime.now());
			entity.setModifiedBy(dto.getUsername());
			return entity;
		}
	}
}
