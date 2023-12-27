package com.NkosopaForum.NkosopaForum.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.NkosopaForum.NkosopaForum.Entity.CommentEntity;
import com.NkosopaForum.NkosopaForum.Entity.Post;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
	List<CommentEntity> findByPost(Post post);

	List<CommentEntity> findByPost(Optional<Post> post);
	
    @Modifying
    @Query("DELETE FROM CommentEntity c WHERE c.user.id = :userId")
    void deleteAllByUserId(@Param("userId")Long userId);

}

