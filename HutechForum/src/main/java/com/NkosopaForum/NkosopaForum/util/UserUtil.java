package com.NkosopaForum.NkosopaForum.util;

import com.NkosopaForum.NkosopaForum.Entity.User;

public class UserUtil {

	//followed User = user being followed by other user
	//following user = user is following other user
	
    public static final boolean isFollowingUser(User followingUser, User folowedUser) {
        return followingUser.getId().equals(folowedUser.getId());
    }

    public static final boolean isFollowedByUser(User followedUser, User followingUser) {
        return followingUser.getFollowing().contains(followedUser);
    }
}
