package com.NkosopaForum.NkosopaForum.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.NkosopaForum.NkosopaForum.Entity.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long>{
    @Modifying
    @Query("DELETE FROM LikeEntity l WHERE l.user.id = :userId")
    void deleteAllByUserId(@Param("userId")Long userId);

    @Modifying
    @Query("DELETE FROM LikeEntity l WHERE l.post.id = :postId")
    void deleteAllByPostId(@Param("postId")Long postId);
}
