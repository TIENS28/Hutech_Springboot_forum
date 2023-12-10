package com.NkosopaForum.NkosopaForum.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NkosopaForum.NkosopaForum.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
