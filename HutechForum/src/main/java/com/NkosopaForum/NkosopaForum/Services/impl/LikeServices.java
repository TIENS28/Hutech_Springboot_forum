package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NkosopaForum.NkosopaForum.Entity.LikeEntity;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.LikeRepository;
import com.NkosopaForum.NkosopaForum.Repositories.PostRepository;
import com.NkosopaForum.NkosopaForum.Services.iLikeService;

import java.util.Collections;

@Service
public class LikeServices implements iLikeService{

	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public LikeEntity likePosts(Long postId, User user) {
		LikeEntity isLikeExist  = likeRepository.isLikeExist(user.getId(), postId); 
		
		if(isLikeExist!=null) {
			likeRepository.deleteById(postId);
		}
		
		Optional<Post> optionalPost = postRepository.findById(postId);
		if (optionalPost.isPresent()) {
	        Post post = optionalPost.get();

	        LikeEntity likes = new LikeEntity();
	        likes.setPost(post);
	        likes.setUser(user);

	        LikeEntity savedLike = likeRepository.save(likes);

	        post.getLikes().add(savedLike); 
	        postRepository.save(post);
	        return savedLike;
	        
	    } else {
	        return null;
	    }
	}

	@Override
	public List<LikeEntity> getAllLike(Long postId) {
		Optional<Post> optionalPost = postRepository.findById(postId);

	    if (optionalPost.isPresent()) {
	        Post post = optionalPost.get();
	        return post.getLikes();
	    } else {
	        return Collections.emptyList();
	    }
	}
	

}
