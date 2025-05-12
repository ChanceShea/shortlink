package com.shea.admin.dto.req;

import lombok.Data;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/10 22:46
 */
@Data
public class UserUpdateReqDTO {

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
