package com.commuStock.commuStock.enums.user;

import lombok.Getter;

@Getter
public enum EUserType {
    NONE(0, "none"),
    LOCAL(1, "local");

    private final int code;
    private final String name;

    EUserType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
