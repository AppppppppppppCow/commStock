package com.commuStock.commuStock.service;

import java.util.Optional;

import com.commuStock.commuStock.enums.user.EUserStateType;
import com.commuStock.commuStock.enums.user.EUserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.commuStock.commuStock.core.StringHelper;
import com.commuStock.commuStock.dto.user.SignDto;
import com.commuStock.commuStock.entity.user.User;
import com.commuStock.commuStock.enums.user.EUserSignType;
import com.commuStock.commuStock.repository.UserRepository;

import lombok.AllArgsConstructor;


@Slf4j
@Service
@AllArgsConstructor
// @RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringHelper stringHelper;

    public User signProcessHandler(SignDto request) throws Exception {
        User userDto = null;
        try{
            Optional<User> user = userRepository.findByEmail(request.getUsername());
            // 초기값 세팅
            // 차후 어디서 로그인 진행했는지 OAUTH인지 아니면 자체 로그인인지 (ESignType)
            request.setUserType(EUserType.LOCAL);


            userDto = user.orElseGet(() -> registerUser(request));
        }catch (Exception e) {
            log.error("회원가입 에러 >> {}", e.getMessage());
        }
        return userDto;
    }

    /** 
     * 회원 가입 DTO 로직
    */
    public User registerUser(SignDto signRequest){
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signRequest.getPassword());
        
        User user = new User();

        user.setEmail(signRequest.getUsername());
        user.setPassword(encodedPassword);
        user.setType(signRequest.getUserType());
        user.setSignType(EUserSignType.Local);
        user.setGender(signRequest.getGender());
        user.setName("뉴비#" + stringHelper.RandomString(8));
        user.setState(EUserStateType.ACTIVE);
        user.setPoint(0L); //

        return userRepository.save(user);
    }
    
}