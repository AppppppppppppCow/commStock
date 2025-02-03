package com.commuStock.commuStock.dto.user;

import com.commuStock.commuStock.enums.user.EGenderType;
import com.commuStock.commuStock.enums.user.EUserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignDto {
    private String username;
    private String password;

    private EGenderType gender;

    private EUserType userType = null;
}
