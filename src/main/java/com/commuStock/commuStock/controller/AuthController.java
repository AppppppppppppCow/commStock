package com.commuStock.commuStock.controller;

import java.util.Optional;

import com.commuStock.commuStock.core.response.ApiResponse;
import com.commuStock.commuStock.dto.user.UserLogin;
import com.commuStock.commuStock.enums.user.EUserStateType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commuStock.commuStock.dto.user.SignDto;
import com.commuStock.commuStock.entity.user.User;
import com.commuStock.commuStock.provider.JwtTokenProvider;
import com.commuStock.commuStock.repository.UserRepository;
import com.commuStock.commuStock.service.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    @PermitAll
    @GetMapping("/test")
    public String test() {
        return "ttst";
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserLogin loginDto, HttpServletResponse response) throws Exception {
        // 로그인 인증 처리 후, 토큰 발급
        
        Optional<User> userOptional = userRepository.findByEmail(loginDto.getUsername());
        if(userOptional.isEmpty()){
            throw new Exception("돌아가");
        }

        User user = userOptional.get();
        // (여기서는 간단하게 바로 토큰을 발급함, 실제로는 서비스로 인증해야 함)

        // 유저에 대한 추가적인 예외처리 등 필요한 것들 코딩 필요
        if(user.getState().equals(EUserStateType.RESTRICT) || user.getState().equals(EUserStateType.REST)) {
            throw new Exception("돌아가2 벤");
        }


        // 토큰 발급
        String token = jwtTokenProvider.generateToken(loginDto.getUsername());
        Cookie cookie = new Cookie("jwt_token", token);
        
        // 쿠키 설정
        cookie.setHttpOnly(true);  // JavaScript에서 쿠키 접근을 막음
        cookie.setSecure(true);    // HTTPS에서만 쿠키를 전송 (SSL/TLS)
        cookie.setPath("/");       // 쿠키의 유효 범위 (예: "/"이면 모든 경로에서 유효)
        cookie.setMaxAge(3600);  // 쿠키 만료 시간 (초)
        // cookie.setSameSite("Strict");  // SameSite 속성 설정 (XSS 공격 방지)
        
        // 쿠키를 response에 추가
        response.addCookie(cookie);
        return new ApiResponse<>(token);
    }

    @PostMapping("/check")
    public ApiResponse<Boolean> check(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 쿠키 이름이 "jwt_token"인 쿠키를 찾습니다.
                if ("jwt_token".equals(cookie.getName())) {
                    // 해당 쿠키의 값을 반환합니다.
                    token = cookie.getValue();
                }
            }
        }

        // 토큰 검증
        boolean validateToken = jwtTokenProvider.validateToken(token);
        if(!validateToken){
            // 로그인 초기화필요함

            return new ApiResponse<>(false);
        }

        return new ApiResponse<>(true);
    }

    @PostMapping("/register")
    public ApiResponse<Boolean> sign(@RequestBody SignDto singdto) throws Exception {
        System.out.println("회원가입 테스트");
        User user = userService.signProcessHandler(singdto);

        return new ApiResponse<>(user != null);
    }


    // 로그아웃 API (쿠키 삭제)
    @PostMapping("/logout")
    public ApiResponse<Boolean> logout(HttpServletResponse response) {
        // JWT 토큰을 삭제하려면 쿠키를 만료시킴
        Cookie jwtCookie = new Cookie("jwt_token", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);  // 쿠키가 안전하게 처리될 수 있도록 설정
        jwtCookie.setMaxAge(0);     // 만료 시간을 0으로 설정하여 쿠키를 삭제
        jwtCookie.setPath("/");     // 쿠키의 유효 경로 설정

        // 응답에 삭제된 쿠키 추가
        response.addCookie(jwtCookie);

        return new ApiResponse<>(true);
    }

}
