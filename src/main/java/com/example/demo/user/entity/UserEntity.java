package com.example.demo.user.entity;

import java.io.Serializable;

import com.example.demo.user.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "member_table")
public class UserEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(length = 30)
    private String user_name;
    private String userPassword;

    @Column(length = 50)
    private String user_email;
    
    public static UserEntity toGetUserEntity(UserDTO dto) {
		UserEntity entity = new UserEntity();
	    entity.setId(dto.getId());
	    entity.setUserId(dto.getUserId());
	    entity.setUser_name(dto.getUser_name());
	    entity.setUserPassword(dto.getUserPassword());
	    entity.setUser_email(dto.getUser_email());
	    
	    return entity;
	}
    
    public static UserEntity toSaveUserEntity(UserDTO dto) {
    	UserEntity entity = new UserEntity();
        entity.setUserId(dto.getUserId());
        entity.setUser_name(dto.getUser_name());
        entity.setUserPassword(dto.getUserPassword());
        entity.setUser_email(dto.getUser_email());
        
        return entity;
    }

	public Object isAdmin() {
		// TODO Auto-generated method stub
		return null;
	}
}
