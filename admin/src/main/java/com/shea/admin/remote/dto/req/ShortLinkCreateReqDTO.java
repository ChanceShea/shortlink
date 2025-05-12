package com.shea.admin.remote.dto.req;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 短链接创建请求参数
 * @Author: Shea.
 * @Date: 2025/5/11 17:00
 */
@Data
public class ShortLinkCreateReqDTO {

    /**
     * 域名
     */
    private String domain;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 创建类型：0：接口创建  1：控制台创建
     */
    private Boolean createdType;

    /**
     * 有效期类型：0：永久  1：自定义
     */
    private Boolean validDateType;

    /**
     * 有效期
     */
    private LocalDateTime validDate;

    /**
     * 描述
     */
    private String description;
}
