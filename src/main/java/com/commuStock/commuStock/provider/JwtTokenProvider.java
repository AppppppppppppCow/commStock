package com.commuStock.commuStock.provider;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
    
    private final String SECRET_KEY = "testasdwfqsf"; // 비밀키 이건 나중에 프로퍼티에서 불러오도록 변경하는게 좋을듯?
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

     // JWT 토큰 생성
    public String generateToken(String email) {
        System.out.println("토큰 생성기>> " + email);

        Claims claims = Jwts.claims().setSubject(email);  // 기본 subject는 username으로 설정
        claims.put("email", email);


        return Jwts.builder()
                .setClaims(claims)  // Claims를 Payload에 설정
                .setIssuedAt(new Date())  // 토큰 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 만료 시간 (1시간)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 서명
                .compact();
    }

    // JWT 토큰에서 사용자 정보 추출
    public String getUserEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
