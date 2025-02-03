package com.commuStock.commuStock.core.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(200, "success"),
    ERROR(1, "ERROR");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
