package com.commuStock.commuStock.configuration;

import org.springframework.context.annotation.Bean;
// import com.commuStock.likelionproject.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.commuStock.commuStock.filter.JwtAuthenticationFilter;
import com.commuStock.commuStock.provider.JwtTokenProvider;
import com.commuStock.commuStock.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
/**
 * 스프링 시큐리티 기본 설정을 filter chain을 통해 
 */
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("test테스트");
        http.csrf((csrf) -> csrf .disable())
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/auth/login", "/auth/register").permitAll() // 로그인과 회원가입 API는 접근 허용
                .requestMatchers("/api/**").authenticated() // 백엔드 API는 인증 필요
                .anyRequest().authenticated()
            ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            ; // authorizeRequests() 대신 authorizeHttpRequests() 사용 스프링 security 6부터 이거 씀
            // ; // 필터 순서 지정


        // 기본 로그인 페이지 비활성화
        http.formLogin().disable(); // 기본 로그인 페이지 비활성화
        http.httpBasic().disable(); // 기본 HTTP 인증 비활성화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}