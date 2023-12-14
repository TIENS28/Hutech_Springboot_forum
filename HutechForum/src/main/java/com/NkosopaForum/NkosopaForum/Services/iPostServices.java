package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Entity.User;

public interface iPostServices {
	PostDTO save(PostDTO postDTO, Long id);
	void delete(Long id);
	List<PostDTO> findAll();
	List<PostDTO> findPostsByUserId(Long userId);
	List<PostDTO> findAllPostsOrderByCreatedDate();
	List<PostDTO> findAllPostsWithComments();
	List<PostDTO> findAll(User currentUser);
}
