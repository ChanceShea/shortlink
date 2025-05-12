package com.shea.admin.dto.req;

import lombok.Data;

/**
 * @description: 用户注册请求参数
 * @Author: Shea.
 * @Date: 2025/5/10 17:08
 */
@Data
public class UserRegisterReqDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
