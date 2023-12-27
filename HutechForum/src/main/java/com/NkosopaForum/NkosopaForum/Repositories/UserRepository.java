package com.NkosopaForum.NkosopaForum.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.NkosopaForum.NkosopaForum.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findById(Long id);
	Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    @Query("SELECT DISTINCT u FROM User u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
    public List<User> searchUser(@Param("query") String query);
    
    User findByVerificationToken(String verificationToken);
    
	User findByEmailAndVerificationToken(String email, String token);
	
	@Query("SELECT u FROM User u JOIN FETCH u.post WHERE u.id = :userId")
	User findUserWithPosts(@Param("userId") Long userId);
	
    @Query("SELECT u FROM User u WHERE u.role = 'USER'")
	List<User> findAllByRole();
}
