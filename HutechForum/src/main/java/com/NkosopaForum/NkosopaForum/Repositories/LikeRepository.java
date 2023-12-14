package com.NkosopaForum.NkosopaForum.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.NkosopaForum.NkosopaForum.Entity.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long>{
	
	@Query("SELECT l FROM LikeEntity l WHERE l.user.id = :userId AND l.post.id = :postId")
	public LikeEntity isLikeExist(@Param("userId") Long userId, @Param("postId") Long postId);
	
	@Query("SELECT l FROM LikeEntity l WHERE l.post.id=:postId")
	public List<LikeEntity> findByPostId(@Param("postId") Long postId);
}
