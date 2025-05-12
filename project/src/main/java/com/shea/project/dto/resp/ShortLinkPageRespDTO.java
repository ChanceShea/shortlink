package com.shea.project.dto.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 短链接分页返回对象
 * @Author: Shea.
 * @Date: 2025/5/12 14:16
 */
@Data
public class ShortLinkPageRespDTO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 域名
     */
    private String domain;

    /**
     * 短链接
     */
    private String shortUri;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 有效期类型：0：永久  1：自定义
     */
    private Integer validDateType;

    /**
     * 有效期
     */
    private LocalDateTime validDate;

    /**
     * 描述
     */
    private String description;

    /**
     * 网站图标
     */
    private String favicon;
}
