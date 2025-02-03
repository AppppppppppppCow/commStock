package com.commuStock.commuStock.entity.user;

import java.util.Date;

import com.commuStock.commuStock.enums.user.EGenderType;
import com.commuStock.commuStock.enums.user.EUserSignType;

import com.commuStock.commuStock.enums.user.EUserStateType;
import com.commuStock.commuStock.enums.user.EUserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 자동 생성
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid; // pid는 기본 키이고 auto increment로 설정됨
    
    private String email;
    
    private String password;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private EUserType type;

    @Column(name = "sign_type")
    @Enumerated(EnumType.ORDINAL)  // Enum 값의 EnumType.STRING 으로 설정하면 DB에 문자열로 저장됨
    private EUserSignType signType;

    private String name;

    private Date last_name_update_time = null;

    private String introduce = null;
    
    private String home_address = null;
    
    private String phone_number  = null;
    
    private Integer nation_code = 0;
    
    private Boolean agree_type = false;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private EGenderType gender;

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    private EUserStateType state;

    private Long point;

    private short restrict_days;

    private Date restrict_end_time;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date create_time;

    private Date update_time;
    
}
