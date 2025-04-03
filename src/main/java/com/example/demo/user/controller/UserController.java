package com.example.demo.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.service.UserService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // 회원가입 시 ID 중복 체크
    @GetMapping("/idCheck")
    public ResponseEntity<String> idCheck(@RequestParam(name = "user_id") String userId) {
        System.out.println("userId: " + userId);
        
        if (userService.idCheck(userId) == 1) {
            System.out.println("회원가입 불가: " + HttpStatus.CONFLICT);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
        }
        
        System.out.println("회원가입 가능: " + HttpStatus.OK);
        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }
    
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
        System.out.println("회원가입 요청: " + userDTO);
        
        try {
            int userId = userService.insert(userDTO);
            
            if (userId == 1) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("회원가입 실패, 중복된 아이디입니다.");
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("회원가입 실패: " + e.getMessage());
        }
    }
    
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        System.out.println("로그인 요청: " + userDTO);

        if (userDTO.getUserId() == null || userDTO.getUserPassword() == null) {
            System.out.println("❌ 요청 데이터가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"잘못된 요청입니다.\"}");
        }

        Map<String, Object> loginResult = userService.login(userDTO.getUserId(), userDTO.getUserPassword());

        if (loginResult != null) {
            System.out.println("✅ 로그인 성공: " + loginResult.get("userId"));
            return ResponseEntity.ok(loginResult);
        }

        System.out.println("❌ 로그인 실패: 아이디 또는 비밀번호 불일치");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{\"error\": \"아이디 또는 비밀번호가 일치하지 않습니다.\"}");
    }

}
