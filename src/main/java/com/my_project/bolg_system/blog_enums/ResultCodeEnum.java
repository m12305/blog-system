package com.my_project.bolg_system.blog_enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


public enum ResultCodeEnum {
    SUCCESS(200),
    FAIL(-1);

    @Getter
    int code;

    ResultCodeEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}

