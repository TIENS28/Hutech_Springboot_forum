package com.NkosopaForum.NkosopaForum.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.NkosopaForum.NkosopaForum.DTO.CommentDTO;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Services.impl.AuthenticationService;
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
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@GetMapping("/admin/posts")
	public ResponseEntity<List<PostDTO>> findAll(
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "5") int size) {
	    List<PostDTO> postPage = postService.findAll(PageRequest.of(page, size));
	    return ResponseEntity.ok(postPage);
	}

	// get all post and its comments order by created date desc 
	@GetMapping("/allPost")
	public ResponseEntity<List<PostDTO>> getAllPosts() {
		User user = authenticationService.getCurrentUser();

		List<PostDTO> posts = postService.findAllPostsOrderByCreatedDate();
		return ResponseEntity.ok(posts);
	}
	
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
	    PostDTO post = postService.findPostById(postId);
	    return ResponseEntity.ok(post);
	}

	@PostMapping("/newPost")
	public PostDTO savePost(@ModelAttribute PostDTO postDTO,
							@RequestParam(name = "thumbnail", required = false) MultipartFile thumbnail,
						    @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
	    System.out.println("Authorization Header: " + authorizationHeader);
	    return postService.save(postDTO, null, thumbnail);
	}

	@PutMapping("/updatePost/{id}")
	public PostDTO updatePost(@PathVariable Long id, 
							  @RequestBody PostDTO postDTO,
							  @RequestParam(name = "thumbnail", required = false) MultipartFile thumbnail) {
		postDTO.setId(id);
		return postService.save(postDTO, id, thumbnail);

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
	  
    @GetMapping("/user/post")
    public ResponseEntity<List<PostDTO>> getPostsForCurrentUser() {
    	List<PostDTO> posts = postService.getPostsForCurrentUser();
		return ResponseEntity.ok(posts);
   }
    
    @PostMapping("/like/{postId}/{userId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId,
    										@PathVariable Long userId) {

        postService.likePost(postId, userId);

        return ResponseEntity.ok("Post liked successfully");
    }
}