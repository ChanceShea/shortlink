package com.shea.admin.common.enums;

import com.shea.admin.common.convention.errorcode.IErrorCode;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/10 16:00
 */
public enum UserErrorCodeEnum implements IErrorCode {

    USER_TOKEN_FAIL("A000200", "用户token验证失败"),

    USER_NULL("B000200", "用户不存在"),
    USER_EXIST("B000201", "用户已存在"),
    USERNAME_EXIST("B000202", "用户名已存在"),
    USER_SAVE_ERROR("B000202", "用户新增失败"),
    USER_USERNAME_OR_PASSWORD_ERROR("B000202", "用户名或密码错误");

    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
