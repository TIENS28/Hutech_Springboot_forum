package com.NkosopaForum.NkosopaForum.Services;

import java.util.List;

import com.NkosopaForum.NkosopaForum.Entity.LikeEntity;
import com.NkosopaForum.NkosopaForum.Entity.User;

public interface iLikeService {
	public LikeEntity likePosts(Long postId, User user);
	
	public List<LikeEntity> getAllLike(Long postId);
}
