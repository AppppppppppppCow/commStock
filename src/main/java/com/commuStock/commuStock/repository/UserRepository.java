package com.commuStock.commuStock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commuStock.commuStock.entity.user.User;
import org.springframework.data.jpa.repository.Query;

public interface  UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByEmail(String email);

    // pid로 사용자를 조회하는 메서드 (pid가 기본 키이므로 필요 없음, 기본적으로 제공됨)
    Optional<User> findById(Long pid);

//    @Query("SELECT * FROM user WHERE name = ?")
    User findByName(String name);

}
