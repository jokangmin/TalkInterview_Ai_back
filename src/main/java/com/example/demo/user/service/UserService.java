package com.example.demo.user.service;

import java.util.Map;

import com.example.demo.user.dto.UserDTO;

public interface UserService {

	public int idCheck(String userId);

	public int insert(UserDTO userDTO);

	public Map<String, Object> login(String userId, String userPassword);

	void logout(String refreshToken);
	
}
