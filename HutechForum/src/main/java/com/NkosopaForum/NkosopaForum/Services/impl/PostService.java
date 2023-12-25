package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.NkosopaForum.NkosopaForum.Converter.PostConverter;
import com.NkosopaForum.NkosopaForum.Converter.UserConverter;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.Role;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.PostRepository;
import com.NkosopaForum.NkosopaForum.Services.iPostServices;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService implements iPostServices {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private PostConverter postConvert;
	
	@Autowired 
	private AuthenticationService authenticationService;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private CommentServices commentServices;
	
	//another way is get current user by extract jwt token
	@Override
	public PostDTO save(PostDTO postDTO, Long id, MultipartFile thumbnail) {
		
		Post newPost = new Post();
        String thumbnailUrl = authenticationService.uploadImageToCloudinary(thumbnail);
		if (postDTO.getId() == null) {
	        User curentUser = authenticationService.getCurrentUser();
			postDTO.setUser(userConverter.EnitytoDTO(curentUser));
	        postDTO.setThumbnailUrl(thumbnailUrl);
	        
			newPost = postConvert.PostToEntity(postDTO);
		} else {
			Optional<Post> OldoptionalPost = postRepo.findById(postDTO.getId());

			if (OldoptionalPost.isPresent()) {
				Post oldPost = OldoptionalPost.get();
				User currentUser = authenticationService.getCurrentUser();
				if(oldPost.getUser().getId().equals(currentUser.getId())) {
					String newThumbnail = authenticationService.uploadImageToCloudinary(thumbnail);
					newPost.setThumbnailUrl(newThumbnail);
					newPost = postConvert.DtoToEntity(postDTO, oldPost);
				}else {
		            throw new EntityNotFoundException("Use are not post owner");
				}
			}
		}
		postRepo.save(newPost);
		return postConvert.toDTO(newPost);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
	    Optional<Post> optionalPost = postRepo.findById(id);

	    if (optionalPost.isPresent()) {
	        Post post = optionalPost.get();
	        User currentUser = authenticationService.getCurrentUser();

	        if (post.getUser().getId().equals(currentUser.getId()) || currentUser.getRole() == Role.ADMIN) {
	        	commentServices.deleteAllByPostId(id);
	        	postRepo.deletePostById(id);
	        } else {
	            throw new EntityNotFoundException("You are not the post owner, and not an admin");
	        }
	    } else {
	        throw new EntityNotFoundException("Post not found");
	    }
	}


	@Override
	public List<PostDTO> findAll(Pageable pageable) {
	    List<Post> posts = postRepo.findAll();
	    return posts.stream()
	            .map(postConvert::toDTO)
	            .collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> getPostsForCurrentUser() {
	    User currentUser = authenticationService.getCurrentUser();

	    if (currentUser != null) {
	        List<Post> userPosts = currentUser.getPost();
	        return userPosts.stream()
	                .map(postConvert::toDTO)
	                .collect(Collectors.toList());
	    } else {
	        return Collections.emptyList();
	    }
	}
	
	@Override
	public Page<PostDTO> searchPost(String query, Pageable pageable) {
	    Page<PostDTO> postPage = postRepo.searchPost(query, pageable).map(postConvert::toDTO);
	    return postPage;
	}
	
	@Override
	public List<PostDTO> findAllPostsOrderByCreatedDate() {
	    List<Post> posts = postRepo.findAllByOrderByCreatedDateDesc();
	    return posts.stream()
	            .map(postConvert::toDTO)
	            .collect(Collectors.toList());
	}
	

	@Override
    public PostDTO findPostById(Long postId) {
        Optional<Post> postOptional = postRepo.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            return postConvert.toDTO(post);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }
	
	@Transactional
	void deleteAllByUserId(Long userId) {
		postRepo.deleteAllByUserId(userId);
	}
}
