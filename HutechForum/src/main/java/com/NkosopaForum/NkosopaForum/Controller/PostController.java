package com.NkosopaForum.NkosopaForum.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NkosopaForum.NkosopaForum.Converter.PostConverter;
import com.NkosopaForum.NkosopaForum.DTO.CommentDTO;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Services.impl.CommentServices;
import com.NkosopaForum.NkosopaForum.Services.impl.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentServices cmtService;

	@Autowired
	private PostConverter postConverter;

	@PostMapping("/newPost")
	public PostDTO savePost(@RequestBody PostDTO postDTO) {
		return postService.save(postDTO, null);
	}

	@PutMapping("/updatePost/{id}")
	public PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
		postDTO.setId(id);
		return postService.save(postDTO, id);

	}

	@DeleteMapping("/deletePost/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		postService.delete(id);
		return ResponseEntity.ok("Post deleted successfully");
	}

	@PostMapping("/{postId}/addComment")
	public ResponseEntity<CommentDTO> addComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
		commentDTO.setPostId(postId);
		CommentDTO newComment = cmtService.save(commentDTO);
		return ResponseEntity.ok(newComment);
	}

	// get all post and its comments
	@GetMapping("/allPost")
	public ResponseEntity<List<PostDTO>> getAllPosts() {
		List<PostDTO> posts = postService.findAll();

		for (PostDTO postDTO : posts) {
			List<CommentDTO> comments = cmtService.findByPostId(postDTO.getId());
			postDTO.setComments(comments);
		}
		return ResponseEntity.ok(posts);
	}

}