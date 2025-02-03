package com.commuStock.commuStock.enums.user;

import lombok.Getter;

@Getter
public enum EUserSignType {
    None("none", 0),
    Local ("local ", 1);
    
    private final String name;
    private final int val;

    EUserSignType(String name, int value){
        this.name = name;
        this.val = value;
    }

}
