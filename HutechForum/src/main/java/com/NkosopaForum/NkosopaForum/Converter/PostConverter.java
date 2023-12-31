package com.NkosopaForum.NkosopaForum.Converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.NkosopaForum.NkosopaForum.DTO.CommentDTO;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Services.impl.AuthenticationService;
import com.NkosopaForum.NkosopaForum.Services.impl.CommentServices;

@Component
public class PostConverter {
	
	@Autowired
	@Lazy
	private UserConverter userConverter;
	
	@Autowired
	@Lazy
	private CommentServices cmtService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	public Post PostToEntity(PostDTO dto) {
		Post entity = new Post();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setThumbnailUrl(dto.getThumbnailUrl());
		entity.setDescription(dto.getDescription());
		entity.setContent(dto.getContent());
		entity.setCreatedDate(LocalDateTime.now());
		entity.setThumbnailUrl(dto.getThumbnailUrl());
		if (dto.getUser() != null) {
			User user = new User();
			user.setId(dto.getUser().getId());
			user.setFirstName(dto.getUser().getFirstName());
			user.setLastName(dto.getUser().getLastName());
			entity.setUser(user);
		}
		entity.setCreatedBy(dto.getUser().getFullName());
		return entity;
	}

	public Post DtoToEntity(PostDTO dto, Post entity) {
		if(dto.getId() == null){
			return null;
		}else {
		
		entity.setTitle(dto.getTitle());
		entity.setThumbnailUrl(dto.getThumbnailUrl());
		entity.setDescription(dto.getDescription());
		entity.setContent(dto.getContent());
		entity.setModifiedDate(LocalDateTime.now());
		if (dto.getUser() != null) {
			User user = new User();
			user.setId(dto.getUser().getId());
			user.setFirstName(dto.getUser().getFirstName());
			user.setLastName(dto.getUser().getLastName());			
			entity.setUser(user);
			entity.setModifiedBy(dto.getUser().getFullName());
		}
		return entity;
		}
	}

	 public PostDTO toDTO(Post entity) {
	        PostDTO dto = new PostDTO();
	        User currentUser = authenticationService.getCurrentUser();
	        
	        if (entity.getId() != null) {
	            dto.setId(entity.getId());
	        }
	        dto.setTitle(entity.getTitle());
	        dto.setContent(entity.getContent());
	        dto.setDescription(entity.getDescription());
	        dto.setThumbnailUrl(entity.getThumbnailUrl());
	        dto.setCreatedDate(entity.getCreatedDate());
	        dto.setCreatedBy(entity.getCreatedBy());
	        dto.setUser(userConverter.EnitytoDTO(entity.getUser()));
	        dto.setTotalLikes(entity.getLikes().size());
	        dto.setTotalComments(entity.getComments().size());
	        dto.setModifiedDate(entity.getModifiedDate());
	       
			List<CommentDTO> comments = cmtService.findByPostId(dto.getId());
		    dto.setLiked(entity.getLikes().stream().anyMatch(like -> like.getUser().equals(currentUser)));

			dto.setComments(comments);
			
	        return dto;
	 }
	 
	 
	 public List<PostDTO> toDTOList(List<Post> posts) {
	        return posts.stream()
	                .map(this::toDTO)
	                .collect(Collectors.toList());
	 }
}