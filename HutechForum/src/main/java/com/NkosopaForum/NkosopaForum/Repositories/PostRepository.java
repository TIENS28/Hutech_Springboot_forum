package com.NkosopaForum.NkosopaForum.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.NkosopaForum.NkosopaForum.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findAllByOrderByCreatedDateDesc();
	
	@Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments")
    List<Post> findAllWithComments();
	
	@Query("SELECT p FROM Post p WHERE p.user.id = :userId")
	List<Post> findPostsByUserId(@Param("userId") Long userId);
}
