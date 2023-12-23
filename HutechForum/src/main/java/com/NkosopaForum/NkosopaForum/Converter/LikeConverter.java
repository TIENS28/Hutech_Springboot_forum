package com.NkosopaForum.NkosopaForum.Converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.NkosopaForum.NkosopaForum.DTO.LikeDTO;
import com.NkosopaForum.NkosopaForum.DTO.PostDTO;
import com.NkosopaForum.NkosopaForum.DTO.UserDTO;
import com.NkosopaForum.NkosopaForum.Entity.LikeEntity;
import com.NkosopaForum.NkosopaForum.Entity.User;

@Component
public class LikeConverter {

    @Autowired
    @Lazy
    private UserConverter userConverter;

    @Autowired
    @Lazy
    private PostConverter postConverter;

    public LikeEntity DTOToEntity(LikeDTO likeDTO) {
        LikeEntity likeEntity = new LikeEntity();

        likeEntity.setId(likeDTO.getId());

        likeEntity.setUser(userConverter.UserToEntity(likeDTO.getUser()));
        likeEntity.setPost(postConverter.PostToEntity(likeDTO.getPost()));

        return likeEntity;
    }

    public LikeDTO entityToDTO(LikeEntity likeEntity, User currentUser) {
        LikeDTO likeDTO = new LikeDTO();

        likeDTO.setId(likeEntity.getId());
       
        UserDTO userDTO = userConverter.EnitytoDTO(likeEntity.getUser());
        likeDTO.setUser(userDTO);

        PostDTO postDTO = postConverter.toDTO(likeEntity.getPost());
        likeDTO.setPost(postDTO);

        return likeDTO;
    }

//    public List<LikeDTO> entityToDTOs(List<LikeEntity> likeEntities, User user) {
//        return likeEntities.stream()
//                .map(likeEntity -> entityToDTO(likeEntity, user))
//                .collect(Collectors.toList());
//    }
    
    public List<LikeDTO> entityToDTOs(List<LikeEntity> likeEntities, User user) {
    	List<LikeDTO> likeDTOs = new ArrayList<>();
    	 for(LikeEntity like:likeEntities) {
    		 UserDTO uDto = userConverter.EnitytoDTO(like.getUser());
    		 PostDTO pDto = postConverter.toDTO(like.getPost());
    		 
    		 LikeDTO lDto = new LikeDTO();
    		 lDto.setId(like.getId());
    		 lDto.setUser(uDto);
    		 lDto.setPost(pDto);
    		 likeDTOs.add(lDto);
    	 }
        return likeDTOs;
    }


    
}
