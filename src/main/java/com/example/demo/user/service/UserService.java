package com.example.demo.user.service;

import com.example.demo.user.dto.UserDTO;

public interface UserService {

	public int idCheck(String userId);

	public int insert(UserDTO userDTO);

	public UserDTO login(String userId, String userPassword);
	
}
