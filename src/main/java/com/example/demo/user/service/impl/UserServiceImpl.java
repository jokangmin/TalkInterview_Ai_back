package com.example.demo.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;


@Transactional
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public int idCheck(String userId) {
		if (userRepository.existsByUserId(userId)) {
            System.out.println("중복된 ID 있음. 가입불가");
            return 1;
        }
		return 0;
	}

	@Override
	public int insert(UserDTO userDTO) {
		//회원가입시 중복 ID 확인
        if (userRepository.existsByUserId(userDTO.getUserId())) {
            System.out.println("중복된 ID 있음. 가입불가");
            return 1;
        } else {
            // 중복 ID 없음. 가입 로직 실행

            // DTO -> Entity 변환
            UserEntity userEntity = UserEntity.toSaveUserEntity(userDTO);
            userEntity.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));

            // 회원 가입 시 초기화, 사용자 정보 DB 저장
            // 사용자 면접 질문 카드 저장해야함
            UserEntity savedUser = insertInit(userEntity);

            return savedUser.getId(); // 수정된 부분
        }
	}
	
	public UserEntity insertInit(UserEntity userEntity) {
		
		//추후 사용자 면접 질문 카드 엔터티 생성 및 연관관계 설정해야함
		//FavoriteQuestionsEntity favoriteQuestionsEntity = new FavoriteQuestionsEntity();
        //favoriteQuestionsEntity.setUserEntity(userEntity);
        //userEntity.setFavoriteQuestionsEntity(favoriteQuestionsEntity);
		
        // 사용자 저장 getUserId추가
        UserEntity savedUser = userRepository.save(userEntity);
      
        return userEntity;
    }
	
//	// JWT 토큰 생성 & 응답 데이터
//    public Map<String, Object> loginResponseData(UserEntity user) {
//        // JWT 토큰 생성
//        String accessToken = jwtUtil.generateAccessToken(user.getUserId(), user.getId(), user.isAdmin());
//        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId(), user.getId());
//
//        // 응답 데이터
//        Map<String, Object> result = new HashMap<>();
//        result.put("accessToken", accessToken);
//        result.put("refreshToken", refreshToken); // 컨트롤러에서 제거될 예정
//        result.put("userId", user.getUserId());
//        result.put("id", user.getId());
//        result.put("isAdmin", user.isAdmin());
//
//        return result;
//    }

	@Override
	public UserDTO login(String userId, String userPassword) {
		//UserEntity user = authenticate(userId, userPassword);
		//if (user == null) {
        //    throw new RuntimeException("Invalid credentials");
        //}
		//Map<String, Object> result = loginResponseData(user);
		
		// 새로운 로그인 세션 저장
        //loginSessionService.saveLoginSession(
        //    userId,
        //    (String) result.get("accessToken"),
        //    (String) result.get("refreshToken")
        //);
        
        //return result;
		
		UserEntity userEntity = userRepository.findByUserIdAndUserPassword(userId, userPassword);
		
		// 유저가 존재하면 DTO로 변환 후 반환
        if (userEntity != null) {
            return new UserDTO(
                userEntity.getId(),
                userEntity.getUserId(),
                userEntity.getUser_name(),
                userEntity.getUserPassword(),
                userEntity.getUser_email()
            );
        }
		
		return null;
	}
	
//	@Override
//    public void logout(String accessToken, String refreshToken) {
//        if (refreshToken != null) {
//            if (jwtUtil.validateToken(refreshToken)) {
//                String userId = jwtUtil.getUserIdFromToken(refreshToken);
//                loginSessionService.removeLoginSession(userId);
//                
//                Date expiryDate = jwtUtil.getExpirationDateFromToken(refreshToken);
//                blacklistService.addToBlacklist(refreshToken, expiryDate);
//            }
//        }
//    }
}
