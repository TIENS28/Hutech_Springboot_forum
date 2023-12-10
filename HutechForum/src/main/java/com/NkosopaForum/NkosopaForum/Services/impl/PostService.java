package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NkosopaForum.NkosopaForum.Converter.PostConverter;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Repositories.PostRepository;
import com.NkosopaForum.NkosopaForum.Services.iPostServices;

@Service
public class PostService implements iPostServices {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private PostConverter postConvert;
	
	@Override
	public PostDTO save(PostDTO postDTO, Long id) {
		Post newPost = new Post();
		if (postDTO.getId() == null) {
			newPost = postConvert.PostToEntity(postDTO);
		} else {
			Optional<Post> OldoptionalPost = postRepo.findById(postDTO.getId());

			if (OldoptionalPost.isPresent()) {
				Post oldPost = OldoptionalPost.get();
				newPost = postConvert.DtoToEntity(postDTO, oldPost);
			}
		}
		postRepo.save(newPost);
		return postConvert.toDTO(newPost);
	}

	@Override
	public void delete(Long id) {
		Optional<Post> post = postRepo.findById(id);
	    if (post.isPresent()) {
	        Post postToDelete = post.get();
	        postRepo.delete(postToDelete);	
	    } else {
	    	System.out.print("Post not found");
	    }
	}

	@Override
	public List<PostDTO> findAll() {
		List<PostDTO> rs = new ArrayList<>();
		List<Post> posts = postRepo.findAll();
		for(Post item: posts) {
			PostDTO postDto = postConvert.toDTO(item);
			rs.add(postDto);
		}
	    return rs;
	}

}
