package com.NkosopaForum.NkosopaForum.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NkosopaForum.NkosopaForum.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findById(Long id);
	Optional<User> findByEmail(String email);
}
