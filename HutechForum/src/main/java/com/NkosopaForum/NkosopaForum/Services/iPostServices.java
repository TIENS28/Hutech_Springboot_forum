package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Entity.User;

public interface iPostServices {
//	PostDTO save(PostDTO postDTO, Long id);
	void delete(Long id);
	List<PostDTO> findAll();
	List<PostDTO> findAllPostsOrderByCreatedDate();
	List<PostDTO> findAllPostsWithComments();
	List<PostDTO> findAll(User currentUser);
	List<PostDTO> getPostsForCurrentUser();
//	List<PostDTO> searchPost(String query);
	Page<PostDTO> searchPost(String query, Pageable pageable);
	PostDTO save(PostDTO postDTO, Long id, MultipartFile thumbnail);
	
}
