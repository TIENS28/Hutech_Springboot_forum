package com.NkosopaForum.NkosopaForum.util;

import com.NkosopaForum.NkosopaForum.Entity.LikeEntity;
import com.NkosopaForum.NkosopaForum.Entity.Post;
import com.NkosopaForum.NkosopaForum.Entity.User;

public class PostUtil {
	
	public final static boolean isLikedByUser(User user, Post post) {
		
		for(LikeEntity like: post.getLikes()) {
			if(like.getUser().equals(user.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
}
