package com.shea.admin.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shea.admin.common.serialize.PhoneDesensitizationSerializer;
import lombok.Data;

/**
 * @description: 用户返回参数响应
 * @Author: Shea.
 * @Date: 2025/5/10 15:09
 */
@Data
public class UserRespDTO {

    private Long id;

    /**
     * 用户名
     */
    private String username;


    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
