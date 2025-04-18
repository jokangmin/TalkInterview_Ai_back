package com.example.demo.user.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.entity.FavoriteQuestion;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.FavoriteQuestionRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import com.example.demo.util.JwtUtil;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private FavoriteQuestionRepository favoriteQuestionRepository;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public int idCheck(String userId) {
        return userRepository.existsByUserId(userId) ? 1 : 0;
    }
    
    @Override
    public int insert(UserDTO userDTO) {
        if (userRepository.existsByUserId(userDTO.getUserId())) {
            return 1;
        }
        
        UserEntity userEntity = UserEntity.toSaveUserEntity(userDTO);
        userEntity.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
        
        UserEntity savedUser = insertInit(userEntity);
        return savedUser.getId();
    }
    
    public UserEntity insertInit(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
    
    @Override
    public Map<String, Object> login(String userId, String userPassword) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        
        if (userEntity == null || !passwordEncoder.matches(userPassword, userEntity.getUserPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        return generateJwtResponse(userEntity);
    }
    
    private Map<String, Object> generateJwtResponse(UserEntity user) {
        Boolean isAdmin = user.isAdmin() instanceof Boolean ? (Boolean) user.isAdmin() : false;
        String accessToken = jwtUtil.generateAccessToken(user.getUserId(), user.getId().longValue(), isAdmin);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId(), user.getId().longValue());
        
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        result.put("userId", user.getUserId());
        result.put("id", user.getId());
        result.put("isAdmin", isAdmin);
        
        return result;
    }
    
    @Override
    public void logout(String refreshToken) {
        if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            String userId = jwtUtil.getUserIdFromToken(refreshToken);
            jwtUtil.invalidateToken(refreshToken);
        }
    }

    @Override
    public void saveFavoriteQuestion(int memberId, String interviewQ, String answer, String feedback, String category,
            String jobTitle) {
    	
    	// 중복 여부 확인
        boolean exists = favoriteQuestionRepository.existsByMemberIdAndInterviewQ(memberId, interviewQ);
        
        if (exists) {
            throw new RuntimeException("이미 해당 질문이 저장되어 있습니다.");
        }
    	
        FavoriteQuestion saved = favoriteQuestionRepository.save(FavoriteQuestion.builder()
            .memberId(memberId)
            .interviewQ(interviewQ)
            .answer(answer)
            .feedback(feedback)
            .category(category)
            .jobTitle(jobTitle)
            .build());
            
        System.out.println("🔥 저장된 질문 ID: " + saved.getId()); // 실제 저장됐는지 확인용
    }

}
