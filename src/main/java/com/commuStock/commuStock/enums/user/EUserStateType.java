package com.commuStock.commuStock.enums.user;

import lombok.Getter;

@Getter
public enum EUserStateType {
    None(0, "none"),
    ACTIVE(1, "active"),
    RESTRICT(2, "restrict"),
    REST(3, "rest");

    private final int code;
    private final String name;

    EUserStateType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
