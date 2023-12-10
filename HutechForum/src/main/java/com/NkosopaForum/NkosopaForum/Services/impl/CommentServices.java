package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private UserRepository userRepo;

	@Autowired
	private CommentRepository cmtRepo;

	@Override
	public CommentDTO save(CommentDTO dto) {
	    CommentEntity newCmt = commentConverter.toEntity(dto);

	    Optional<Post> post = postRepo.findById(dto.getPostId());
	    Optional<User> user = userRepo.findById(dto.getUserId());

	    if (post.isPresent() && user.isPresent()) {
	        newCmt.setPost(post.get());
	        newCmt.setUser(user.get());
	        newCmt.setCreatedBy(user.get().getUsername());
	    } else {
	        throw new EntityNotFoundException("Post or User not found");
	    }
	    CommentEntity savedComment = cmtRepo.save(newCmt);
	    return commentConverter.toDTO(savedComment);
	}

	@Override
	public List<CommentDTO> findByPostId(Long id) {
		List<CommentDTO> rs = new ArrayList<>();
		Optional<Post> post = postRepo.findById(id);
		List<CommentEntity> entity = cmtRepo.findByPost(post);
		for (CommentEntity item : entity) {
			CommentDTO commentDTO = commentConverter.toDTO(item);
			rs.add(commentDTO);
		}
		return rs;
	}

}
