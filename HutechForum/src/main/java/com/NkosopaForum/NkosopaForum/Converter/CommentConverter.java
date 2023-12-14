package com.NkosopaForum.NkosopaForum.Converter;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.NkosopaForum.NkosopaForum.DTO.CommentDTO;
import com.NkosopaForum.NkosopaForum.Entity.CommentEntity;

@Component
public class CommentConverter {

	@Autowired
	@Lazy
	private UserConverter userConverter;

	public CommentEntity toEntity(CommentDTO dto) {
		CommentEntity entity = new CommentEntity();

		entity.setId(dto.getId());
		entity.setContent(dto.getContent());
		entity.setCreatedDate(LocalDateTime.now());

		return entity;
	}

	public CommentDTO toDTO(CommentEntity entity) {
		CommentDTO dto = new CommentDTO();

		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setUserId(entity.getUser().getId());
		dto.setPostId(entity.getPost().getId());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setUser(userConverter.EnitytoDTO(entity.getUser()));
		return dto;
	}
}
