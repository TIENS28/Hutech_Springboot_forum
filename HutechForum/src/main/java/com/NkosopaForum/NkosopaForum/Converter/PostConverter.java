package com.NkosopaForum.NkosopaForum.Converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.User;

@Component
public class PostConverter {
	
	@Autowired
	@Lazy
	private UserConverter userConverter;

	public Post PostToEntity(PostDTO dto) {
		Post entity = new Post();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setThumbnail(dto.getThumbnail());
		entity.setDescription(dto.getDescription());
		entity.setContent(dto.getContent());
		entity.setCreatedDate(LocalDateTime.now());
		if (dto.getUser() != null) {
			User user = new User();
			user.setId(dto.getUser().getId());
			user.setFirstName(dto.getUser().getFirstName());
			user.setLastName(dto.getUser().getLastName());
			entity.setUser(user);
		}
		
		entity.setCreatedBy(dto.getUser().getUsername());
		return entity;
	}

	public Post DtoToEntity(PostDTO dto, Post entity) {
		if(dto.getId() == null){
			return null;
		}else {
		
		entity.setTitle(dto.getTitle());
		entity.setThumbnail(dto.getThumbnail());
		entity.setDescription(dto.getDescription());
		entity.setContent(dto.getContent());
		entity.setModifiedDate(LocalDateTime.now());
		if (dto.getUser() != null) {
			User user = new User();
			user.setId(dto.getUser().getId());
			user.setFirstName(dto.getUser().getFirstName());
			user.setLastName(dto.getUser().getLastName());			
			entity.setUser(user);
			entity.setModifiedBy(dto.getUser().getUsername());
		}
		return entity;
		}
	}

	public PostDTO toDTO(Post entity) {
		PostDTO dto = new PostDTO();

		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setTitle(entity.getTitle());
		dto.setContent(entity.getContent());
		dto.setDescription(entity.getDescription());
		dto.setThumbnail(entity.getThumbnail());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setModifiedBy(entity.getUser().getUsername());
		dto.setUser(userConverter.EnitytoDTO(entity.getUser()));
		
		return dto;
	}

	public static List<PostDTO> toDTOList(List<Post> posts) {
		PostConverter converter = new PostConverter();
		return posts.stream().map(converter::toDTO).collect(Collectors.toList());
	}
}
