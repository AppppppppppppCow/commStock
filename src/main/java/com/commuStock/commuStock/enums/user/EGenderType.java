package com.commuStock.commuStock.enums.user;

import lombok.Getter;

@Getter
public enum EGenderType {
    None(0, "none"),
    Male(1, "male"),
    Female(2, "female");

    private final int code;
    private final String name;

    EGenderType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
