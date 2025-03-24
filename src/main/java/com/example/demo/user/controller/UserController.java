package com.example.demo.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	// 회원가입시 ID 중복 체크
    @GetMapping("/idCheck")
    public ResponseEntity<String> idCheck(@RequestParam(name = "user_id") String user_id) {
        System.out.println("userId" + user_id);
        int idCheck = userService.idCheck(user_id);

        if (idCheck == 1) {
            System.out.println("회원가입 불가 " + HttpStatus.CONFLICT);
            return new ResponseEntity<>("이미 사용 중인 아이디입니다.", HttpStatus.CONFLICT); // 409 Conflict
        } else {
            System.out.println("회원가입 가능 " + HttpStatus.OK);
            return new ResponseEntity<>("사용 가능한 아이디입니다.", HttpStatus.OK); // 200 OK
        }
    }
    
	//회원가입
	@PostMapping("/join")
	public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO){
		System.out.println("signUp: " + userDTO);
		int userId = userService.insert(userDTO);
        try {
            if (userId == 1) {
                return new ResponseEntity<>("회원가입 실패, 중복된 아이디입니다.", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("회원가입 성공: " + userId, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입 후 쿠폰 발급 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	// 로그인 기능 추가
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        System.out.println("로그인 요청: " + userDTO);
        
        UserDTO loginUser = userService.login(userDTO.getUserId(), userDTO.getUserPassword());
        //Map<String, Object> loginResult = userService.login(userDTO.getUserId(), userDTO.getUserPassword());

        // Refresh Token을 쿠키에 설정
//        Cookie refreshTokenCookie = new Cookie("refreshToken",
//                (String) loginResult.get("refreshToken"));
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setPath("/");
//        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일
//        response.addCookie(refreshTokenCookie);
        //loginResult.remove("refreshToken");
        
        if (loginUser != null) {
            System.out.println("로그인 성공: " + loginUser.getUserId());
            return ResponseEntity.ok(loginUser); // 200 OK
        } else {
            System.out.println("로그인 실패: 아이디 또는 비밀번호 불일치");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{\"error\": \"아이디 또는 비밀번호가 일치하지 않습니다.\"}");
        }
    }
    
    // 로그아웃
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(
//        @RequestHeader("Authorization") String accessToken,
//        HttpServletRequest request,
//        HttpServletResponse response) {
//        
//        // 리프레시 토큰 쿠키 가져오기
//        Cookie[] cookies = request.getCookies();
//        String refreshToken = null;
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("refreshToken".equals(cookie.getName())) {
//                    refreshToken = cookie.getValue();
//                    break;
//                }
//            }
//        }
//
//        // 로그아웃 처리
//        userService.logout(accessToken, refreshToken);
//
//        // 리프레시 토큰 쿠키 삭제
//        Cookie cookie = new Cookie("refreshToken", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        return ResponseEntity.ok("로그아웃 성공");
//    }
}
