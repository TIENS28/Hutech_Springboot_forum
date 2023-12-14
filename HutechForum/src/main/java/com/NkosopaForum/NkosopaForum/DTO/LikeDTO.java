package com.NkosopaForum.NkosopaForum.DTO;

public class LikeDTO extends BaseDTO<LikeDTO>{
	
	private UserDTO user;
	private PostDTO post;
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public PostDTO getPost() {
		return post;
	}
	public void setPost(PostDTO post) {
		this.post = post;
	}
	
	

}
