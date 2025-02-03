package com.commuStock.commuStock.dto.user;

import com.commuStock.commuStock.enums.user.EGenderType;
import com.commuStock.commuStock.enums.user.EUserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin {
    private String username;
    private String password;
}

