package com.NkosopaForum.NkosopaForum.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NkosopaForum.NkosopaForum.Entity.CommentEntity;
import com.NkosopaForum.NkosopaForum.Entity.Post;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
	List<CommentEntity> findByPost(Post post);

	List<CommentEntity> findByPost(Optional<Post> post);
}
