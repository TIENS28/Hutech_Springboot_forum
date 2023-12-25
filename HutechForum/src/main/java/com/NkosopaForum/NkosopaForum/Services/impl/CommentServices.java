package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NkosopaForum.NkosopaForum.Converter.CommentConverter;
import com.NkosopaForum.NkosopaForum.DTO.CommentDTO;
import com.NkosopaForum.NkosopaForum.Entity.CommentEntity;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.CommentRepository;
import com.NkosopaForum.NkosopaForum.Repositories.PostRepository;
import com.NkosopaForum.NkosopaForum.Repositories.UserRepository;
import com.NkosopaForum.NkosopaForum.Services.iCommentService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentServices implements iCommentService {

	@Autowired
	private CommentConverter commentConverter;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private CommentRepository cmtRepo;

	@Autowired	
	private AuthenticationService authenticationService;

	@Override
	public CommentDTO save(CommentDTO dto) {

		User currentUser = authenticationService.getCurrentUser();
		CommentEntity newCmt = commentConverter.toEntity(dto, currentUser);

		Optional<Post> post = postRepo.findById(dto.getPostId());

		if (post.isPresent()) {
			newCmt.setPost(post.get());
			newCmt.setCreatedBy(currentUser.getUsername());
		} else {
			throw new EntityNotFoundException("Post not found");
		}

		CommentEntity savedComment = cmtRepo.save(newCmt);
		return commentConverter.toDTO(savedComment);
	}

	@Override
	public List<CommentDTO> findByPostId(Long id) {
		List<CommentDTO> rs = new ArrayList<>();
		Optional<Post> post = postRepo.findById(id);

		if (post.isPresent()) {
			List<CommentEntity> entities = cmtRepo.findByPost(post.get());

			for (CommentEntity entity : entities) {
				if (entity.getUser() != null) {
					CommentDTO commentDTO = commentConverter.toDTO(entity);
					rs.add(commentDTO);
				}
			}
		}
		return rs;
	}
	
	@Transactional
	public void deleteAllByUserId(Long userId) {
	    cmtRepo.deleteAllByUserId(userId);
	}
	
	@Transactional
	public void deleteAllByPostId(Long postId) {
		cmtRepo.deleteAllByPostId(postId);
	}
	
}
