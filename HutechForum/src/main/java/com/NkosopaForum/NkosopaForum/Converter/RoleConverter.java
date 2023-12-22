package com.NkosopaForum.NkosopaForum.Converter;
import org.springframework.stereotype.Component;

import com.NkosopaForum.NkosopaForum.DTO.RoleDTO;
import com.NkosopaForum.NkosopaForum.Entity.Role;

@Component
public class RoleConverter {


    public static Role toEntity(RoleDTO roleDTO) {
        return Role.valueOf(roleDTO.getRoleCode());
    }
    
    
    public RoleDTO toDTO(Role entity) {
        if (entity == null) {
            return null; 
        }

        return RoleDTO.builder()
                .roleCode(entity.name())  
                .build();
    }


}
