package com.NkosopaForum.NkosopaForum.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDTO extends BaseDTO<RoleDTO> {
	
    private String roleCode;
    	
}
