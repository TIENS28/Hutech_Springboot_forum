package com.NkosopaForum.NkosopaForum.Services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NkosopaForum.NkosopaForum.Converter.UserConverter;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.User;
import com.NkosopaForum.NkosopaForum.Repositories.UserRepository;
import com.NkosopaForum.NkosopaForum.Services.iUserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements iUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserConverter userConverter;

	@Override
	public UserDTO save(UserDTO userDTO, Long id) {
		User newUser = new User();
		//user not exist yet, create new user
		if(userDTO.getId() == null) {
			newUser = userConverter.UserToEntity(userDTO);
			
		}else {
			//update user information
			Optional<User> optionalUser = userRepo.findById(userDTO.getId());
			if(optionalUser.isPresent()) {
				User oldUser = optionalUser.get();
				newUser = userConverter.DtoToEnity(userDTO, oldUser);
			}
		}
		
		userRepo.save(newUser);
		return userConverter.EnitytoDTO(newUser);
	}

	@Override
	public void delete(Long id) {
		 Optional<User> optionalUser = userRepo.findById(id);
		    
		    if (optionalUser.isPresent()) {
		        User user = optionalUser.get();
		        user.getPost().clear();
		        
		        userRepo.deleteById(id);
		    } else {
		        // Handle the case where the user with the given ID is not found.
		        throw new EntityNotFoundException("User not found with ID: " + id);
		    }
	}

	@Override
	public List<UserDTO> findAll() {
		return null;
	}

	
}
