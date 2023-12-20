package com.NkosopaForum.NkosopaForum.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NkosopaForum.NkosopaForum.DTO.CommentDTO;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Services.impl.CommentServices;
import com.NkosopaForum.NkosopaForum.Services.impl.PostService;

@RestController
@RequestMapping("/api/auth/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentServices cmtService;
	

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
	
	@GetMapping("/searchPost/{query}")
	public ResponseEntity<Page<PostDTO>> searchPost(
	    @PathVariable String query,
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "5") int size
	) {
	    Page<PostDTO> postPage = postService.searchPost(query, PageRequest.of(page, size));
	    return ResponseEntity.ok(postPage);
	}
	
	@GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long postId) {
        List<CommentDTO> comments = cmtService.findByPostId(postId);
        return ResponseEntity.ok(comments);
    }
	
	// get all post and its comments order by created date desc 
	@GetMapping("/allPost")
	public ResponseEntity<List<PostDTO>> getAllPosts() {
		List<PostDTO> posts = postService.findAllPostsOrderByCreatedDate();
		return ResponseEntity.ok(posts);
	}
	
	  
    @GetMapping("/user/post")
    public ResponseEntity<List<PostDTO>> getPostsForCurrentUser() {
    	List<PostDTO> posts = postService.getPostsForCurrentUser();
		return ResponseEntity.ok(posts);
    }
}