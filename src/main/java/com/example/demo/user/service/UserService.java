package com.example.demo.user.service;

import java.util.Map;

import com.example.demo.user.dto.UserDTO;

public interface UserService {

	public int idCheck(String userId);

	public int insert(UserDTO userDTO);

	public Map<String, Object> login(String userId, String userPassword);

	void logout(String refreshToken);

	public void saveFavoriteQuestion(int memberId, String interviewQ, String answer, String feedback, String category,
			String jobTitle);
	
}
