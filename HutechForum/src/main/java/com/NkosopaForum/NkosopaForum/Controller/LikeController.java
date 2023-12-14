package com.NkosopaForum.NkosopaForum.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.NkosopaForum.NkosopaForum.Entity.LikeEntity;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Services.impl.LikeServices;

@RestController
@RequestMapping("/api/auth/likes")
public class LikeController {

    @Autowired
    private LikeServices likeServices;

    @PostMapping("/likePost/{postId}")
    public LikeEntity likePost(@PathVariable Long postId, @RequestBody User user) {
        return likeServices.likePosts(postId, user);
    }

    @GetMapping("/getAllLikes/{postId}")
    public List<LikeEntity> getAllLikes(@PathVariable Long postId) {
        return likeServices.getAllLike(postId);
    }
}
	