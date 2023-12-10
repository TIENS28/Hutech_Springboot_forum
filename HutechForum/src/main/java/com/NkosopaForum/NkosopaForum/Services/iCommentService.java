package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import com.NkosopaForum.NkosopaForum.DTO.CommentDTO;

public interface iCommentService {
	CommentDTO save(CommentDTO dto);
	List<CommentDTO> findByPostId(Long id);
}
