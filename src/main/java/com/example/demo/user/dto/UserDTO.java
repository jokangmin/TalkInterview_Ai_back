package com.example.demo.user.dto;

import com.example.demo.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Integer id;
    private String userId;
    private String user_name;	
    private String UserPassword;
    private String user_email;
    
    public static UserDTO toGetUserDTO(UserEntity entity) {
    	UserDTO dto = new UserDTO();
    	dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setUser_name(entity.getUser_name());
        dto.setUserPassword(entity.getUserPassword());
        dto.setUser_email(entity.getUser_email());
        return dto;
    }
    
    public static UserDTO toSaveUserDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        dto.setUser_name(entity.getUser_name());
        dto.setUserPassword(entity.getUserPassword());
        dto.setUser_email(entity.getUser_email());
        return dto;
    }
}
