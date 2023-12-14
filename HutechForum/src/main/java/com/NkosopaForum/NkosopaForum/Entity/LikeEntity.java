package com.NkosopaForum.NkosopaForum.Entity;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "'Like'")
public class LikeEntity extends BaseEntity<LikeEntity> {
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Post post;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
	
}
