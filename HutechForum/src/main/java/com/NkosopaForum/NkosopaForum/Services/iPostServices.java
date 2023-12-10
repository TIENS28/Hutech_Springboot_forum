package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import com.NkosopaForum.NkosopaForum.DTO.PostDTO;

public interface iPostServices {
	PostDTO save(PostDTO postDTO, Long id);
	void delete(Long id);
	List<PostDTO> findAll();
}
